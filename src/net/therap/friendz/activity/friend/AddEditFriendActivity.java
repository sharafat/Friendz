package net.therap.friendz.activity.friend;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import com.google.inject.Inject;
import net.therap.friendz.R;
import net.therap.friendz.controller.FriendController;
import net.therap.friendz.domain.Friend;
import roboguice.activity.RoboActivity;
import roboguice.inject.ContentView;
import roboguice.inject.InjectResource;
import roboguice.inject.InjectView;

import java.io.Serializable;

import static net.therap.friendz.util.Validation.validateRequired;

@ContentView(R.layout.add_edit_friend)
public class AddEditFriendActivity extends RoboActivity {
    @Inject
    private FriendController friendController;

    @InjectView(R.id.name)
    private EditText nameInput;
    @InjectView(R.id.email)
    private EditText emailInput;
    @InjectView(R.id.save_button)
    private Button saveButton;

    @InjectResource(R.string.required)
    private String required;

    private Friend friend;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        friend = new Friend();

        Serializable object = getIntent().getSerializableExtra(FriendListActivity.KEY_FRIEND);
        if (object != null) {   //edit screen
            setTitle(R.string.edit_friend);

            friend = (Friend) object;
            nameInput.setText(friend.getName());
            emailInput.setText(friend.getEmail());
        }
    }

    public void save(View view) {
        if (!validate()) {
            return;
        }

        friend.setName(nameInput.getText().toString());
        friend.setEmail(emailInput.getText().toString());

        friendController.save(friend);

        startActivity(new Intent(this, FriendListActivity.class));
        finish();
    }

    private boolean validate() {
        return validateRequired(nameInput, required) & validateRequired(emailInput, required);
    }
}
