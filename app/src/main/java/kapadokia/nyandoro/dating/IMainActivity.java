package kapadokia.nyandoro.dating;


import kapadokia.nyandoro.dating.models.Message;
import kapadokia.nyandoro.dating.models.User;

/**
 * Created by User on 1/24/2018.
 */

public interface IMainActivity {

    void inflateViewProfileFragment(User user);

    void onMessageSelected(Message message);
}
