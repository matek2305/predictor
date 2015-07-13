package utils.settings;

/**
 * Predictor settings.
 *
 * @author Mateusz Urbański <matek2305@gmail.com>
 */
public final class PredictorSettings {

    public enum Setting {

        /**
         * Authentication token expiration time (in minutes).
         */
        AUTH_TOKEN_EXPIRATION_DATE("auth-token-expiration-time"),

        /**
         * Prediction block time (in minutes);
         */
        PREDICTION_BLOCK_TIME("prediction-block-time"),

        /**
         * Amount of points for precise prediction.
         */
        PREDICTION_FULL_POINTS("prediction-points-full"),

        /**
         * Amount of points for match winner prediction.
         */
        PREDICTION_WINNER_POINTS("prediction-points-winner"),

        /**
         * Amount of points for draw prediction.
         */
        PREDICTION_DRAW_POINTS("prediction-points-draw"),

        /**
         * Amount of points for missed prediction.
         */
        PREDICTION_MISSED_POINTS("prediction-points-missed"),

        /**
         * Competition secuirty code length.
         */
        COMPETITION_SECURITY_CODE_LENGTH("competititon-security-code-length")
        ;

        private final String name;

        Setting(final String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

    }

    private static SettingsProvider settingsProvider = new DefaultSettingsProvider();

    public static final int getInt(Setting setting) {
        return settingsProvider.getInt(setting);
    }

    public static void setSettingsProvider(SettingsProvider settingsProvider) {
        PredictorSettings.settingsProvider = settingsProvider;
    }

    private PredictorSettings() {

    }
}
