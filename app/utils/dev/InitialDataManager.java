package utils.dev;

import org.reflections.Reflections;
import play.Logger;
import play.db.jpa.JPA;
import play.libs.Yaml;

import javax.persistence.Entity;
import java.util.*;

import static java.lang.String.format;

/**
 * Created by Hatake on 2014-12-14.
 */
public class InitialDataManager {

    private static final String DROP_DATA_FROM_TABLE_PATTERN = "DELETE FROM %s";

    private static final Map<Integer, InitialDataWrapper> initialDataMap = new TreeMap<>();
    private static final Map<Integer, InitialDataWrapper> initialDataDropOrderMap = new TreeMap<>(Collections.reverseOrder());
    private static final Map<String, Map<String, List<Object>>> filesDataMap = new HashMap<>();

    public void inserData() {
        if (initialDataMap.isEmpty()) {
            prepareInitialDataMap();
        }

        for (Map.Entry<Integer, InitialDataWrapper> entry : initialDataMap.entrySet()) {
            insertData(entry.getValue());
        }
    }

    public void dropData() {
        if (initialDataMap.isEmpty()) {
            prepareInitialDataMap();
        }

        for (Map.Entry<Integer, InitialDataWrapper> entry : initialDataDropOrderMap.entrySet()) {
            dropData(entry.getValue());
        }
    }

    private void insertData(InitialDataWrapper data) {
        List<Object> entities = getDataMap(data.getData()).get(data.getData().name());
        persist(entities);

        Logger.debug("{}: {} rows inserted", data.getTarget().getSimpleName(), entities.size());
    }

    private <T> void persist(List<T> entities) {
        for (T entity : entities) {
            JPA.em().persist(entity);
        }
    }

    private void prepareInitialDataMap() {
        Reflections reflections = new Reflections("domain");
        Set<Class<?>> initialDataClasses = reflections.getTypesAnnotatedWith(InitialData.class);
        for (Class<?> initialDataClass : initialDataClasses) {
            if (!initialDataClass.isAnnotationPresent(Entity.class)) {
                Logger.warn("Skipping initial data for class: {}! Marked class must be an entity.", initialDataClass.getSimpleName());
                continue;
            }

            InitialData initialData = initialDataClass.getAnnotation(InitialData.class);
            InitialDataWrapper wrapper = InitialDataWrapper.wrap(initialDataClass, initialData);
            initialDataMap.put(initialData.order(), wrapper);
            initialDataDropOrderMap.put(initialData.order(), wrapper);
        }
    }

    private void dropData(InitialDataWrapper data) {
        String query = format(DROP_DATA_FROM_TABLE_PATTERN, data.getTarget().getSimpleName());
        int droppedCount = JPA.em().createQuery(query).executeUpdate();
        Logger.debug("{}: dropped {} rows", data.getTarget().getSimpleName(), droppedCount);
    }

    private Map<String, List<Object>> getDataMap(InitialData initialData) {
        if (!filesDataMap.containsKey(initialData.fileName())) {
            Map<String, List<Object>> dataMap = (Map<String, List<Object>>) Yaml.load(initialData.fileName());
            filesDataMap.put(initialData.fileName(), dataMap);
        }

        return filesDataMap.get(initialData.fileName());
    }

    private static class InitialDataWrapper {

        public static InitialDataWrapper wrap(Class<?> target, InitialData data) {
            return new InitialDataWrapper(target, data);
        }

        private final Class<?> target;
        private final InitialData data;

        private InitialDataWrapper(Class<?> target, InitialData data) {
            this.target = target;
            this.data = data;
        }

        public Class<?> getTarget() {
            return target;
        }

        public InitialData getData() {
            return data;
        }
    }
}
