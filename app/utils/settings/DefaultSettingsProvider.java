package utils.settings;

import play.Play;

/**
 * Created by Hatake on 2015-02-15.
 */
public class DefaultSettingsProvider implements SettingsProvider {

    @Override
    public int getInt(PredictorSettings.Setting setting) {
        return Play.application().configuration().getInt(setting.getName());
    }
}
