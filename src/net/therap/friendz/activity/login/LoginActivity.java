package net.therap.friendz.activity.login;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import com.google.inject.Inject;
import net.therap.friendz.R;
import net.therap.friendz.activity.friend.FriendListActivity;
import net.therap.friendz.controller.LoginController;
import roboguice.activity.RoboActivity;
import roboguice.inject.ContentView;
import roboguice.inject.InjectResource;
import roboguice.inject.InjectView;

import static net.therap.friendz.util.Validation.validateRequired;

@ContentView(R.layout.login)
public class LoginActivity extends RoboActivity {
    @Inject
    private LoginController loginController;

    @InjectView(R.id.email)
    private EditText emailInput;
    @InjectView(R.id.password)
    private EditText passwordInput;
    @InjectView(R.id.login_button)
    private Button loginButton;

    @InjectResource(R.string.required)
    private String required;
    @InjectResource(R.string.app_name)
    private String appName;
    @InjectResource(R.string.incorrect_credentials)
    private String incorrectCredentials;
    @InjectResource(R.string.ok)
    private String ok;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public void login(View view) {
        if (!validate()) {
            return;
        }

        if (loginController.login(emailInput.getText().toString(), passwordInput.getText().toString())) {
            startActivity(new Intent(this, FriendListActivity.class));
            finish();
        } else {
            new AlertDialog.Builder(this)
                    .setTitle(appName)
                    .setMessage(incorrectCredentials)
                    .setPositiveButton(ok, null)
                    .create()
                    .show();
        }
    }

    private boolean validate() {
        return validateRequired(emailInput, required) & validateRequired(passwordInput, required);
    }
}
