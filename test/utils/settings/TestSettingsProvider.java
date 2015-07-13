package utils.settings;

import com.google.common.collect.ImmutableMap;

import java.util.Map;
import java.util.Optional;

/**
 * Created by Hatake on 2015-07-13.
 */
public class TestSettingsProvider implements SettingsProvider {

    private final static Map<PredictorSettings.Setting, Integer> INT_SETTINGS_MAP = new ImmutableMap.Builder<PredictorSettings.Setting, Integer>()
            .put(PredictorSettings.Setting.AUTH_TOKEN_EXPIRATION_DATE, 30)
            .build();

    @Override
    public int getInt(PredictorSettings.Setting setting) {
        return Optional.ofNullable(INT_SETTINGS_MAP.get(setting)).orElse(0);
    }
}