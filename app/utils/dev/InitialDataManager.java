package utils.dev;

import org.reflections.Reflections;
import play.Logger;
import play.db.jpa.JPA;
import play.libs.Yaml;

import javax.persistence.Entity;
import javax.persistence.Table;
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

        Logger.debug(format("%s: %s rows inserted", data.getTarget().getSimpleName(), entities.size()));
    }

    private <T> void persist(List<T> entities) {
        for (T entity : entities) {
            JPA.em().persist(entity);
        }
    }

    private void prepareInitialDataMap() {
        Reflections reflections = new Reflections("models");
        Set<Class<?>> initialDataClasses = reflections.getTypesAnnotatedWith(InitialData.class);
        for (Class<?> initialDataClass : initialDataClasses) {
            if (!initialDataClass.isAnnotationPresent(Entity.class)) {
                Logger.warn("Skipping initial data for class: " + initialDataClass.getSimpleName() + "! Marked class must be an entity.");
                continue;
            }

            InitialData initialData = initialDataClass.getAnnotation(InitialData.class);
            InitialDataWrapper wrapper = InitialDataWrapper.wrap(initialDataClass, initialData);
            initialDataMap.put(initialData.order(), wrapper);
            initialDataDropOrderMap.put(initialData.order(), wrapper);
        }
    }

    private void dropData(InitialDataWrapper data) {
        String table = getTableName(data.getTarget());
        String query = format(DROP_DATA_FROM_TABLE_PATTERN, table);

        int droppedCount = JPA.em().createNativeQuery(query).executeUpdate();
        Logger.debug(format("%s [%s]: dropped %s rows", data.getTarget().getSimpleName(), table, droppedCount));
    }

    private Map<String, List<Object>> getDataMap(InitialData initialData) {
        if (!filesDataMap.containsKey(initialData.fileName())) {
            Map<String, List<Object>> dataMap = (Map<String, List<Object>>) Yaml.load(initialData.fileName());
            filesDataMap.put(initialData.fileName(), dataMap);
        }

        return filesDataMap.get(initialData.fileName());
    }

    private String getTableName(Class<?> entityClass) {
        if (entityClass.isAnnotationPresent(Table.class)) {
            return entityClass.getAnnotation(Table.class).name();
        }

        Logger.warn(format("No table name specified for %s entity, using default.", entityClass.getSimpleName()));
        return entityClass.getSimpleName();
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