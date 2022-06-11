package com.pavlovic.cubes.events24.ui.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;

import com.pavlovic.cubes.events24.data.DataContainer;
import com.pavlovic.cubes.events24.data.model.User;
import com.pavlovic.cubes.events24.ui.view.EventsMessage;
import com.pavlovic.cubes.events24.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.messaging.FirebaseMessaging;
import com.pavlovic.cubes.events24.data.SharedPrefs;
import com.pavlovic.cubes.events24.ui.activity.EditProfileActivity;
import com.pavlovic.cubes.events24.ui.activity.HomeActivity;
import com.pavlovic.cubes.events24.ui.activity.SplashscreenActivity;
import com.pavlovic.cubes.events24.databinding.FragmentProfileBinding;
import com.squareup.picasso.Picasso;

public class ProfileFragment extends Fragment {

    private FragmentProfileBinding binding;

    public ProfileFragment() {
        // Required empty public constructor
    }

    public static ProfileFragment newInstance() {
        ProfileFragment fragment = new ProfileFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentProfileBinding.inflate(inflater,container,false);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

//        String[] array = getResources().getStringArray(R.array.app_languages);
//        int index = SharedPrefs.getInstance(getActivity()).getAppLanguage();
//        String language = array[index];

        binding.textViewLanguageChange.
                setText(getResources().getStringArray(R.array.app_languages)[SharedPrefs.getInstance(getActivity()).getAppLanguage()]);
        binding.switchButton.setChecked(SharedPrefs.getInstance(getActivity()).isNotificationOn());

        binding.circleImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), EditProfileActivity.class));
            }
        });

        binding.textViewName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), EditProfileActivity.class));
            }
        });

        binding.textViewMail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), EditProfileActivity.class));
            }
        });

        binding.buttonLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth auth = FirebaseAuth.getInstance();
                auth.signOut();

                getActivity().finish();
                startActivity(new Intent(getContext(), SplashscreenActivity.class));
            }
        });

        binding.textViewTermsAndConditions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EventsMessage.showMessage(getContext(),"In progress..");
            }
        });

        binding.textViewHelpCentre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EventsMessage.showMessage(getContext(),"In progress..");
            }
        });

        binding.textViewLanguageChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int selectedIndex = SharedPrefs.getInstance(getActivity()).getAppLanguage();
                String[] array = getResources().getStringArray(R.array.app_languages);

                if (selectedIndex == array.length-1){
                    //Dosao sam do kraja niza, vrati index na nulu!
                    SharedPrefs.getInstance(getActivity()).saveAppLanguage(0);
                    binding.textViewLanguageChange.setText(array[0]);
                }
                else {
                    selectedIndex++;
                    SharedPrefs.getInstance(getActivity()).saveAppLanguage(selectedIndex);
                    binding.textViewLanguageChange.setText(array[selectedIndex]);
                }
                //Ispitati koji je jezik selektovan, i da se na nivou app promeni Configuraciona Lokalizacija.

                Intent i = new Intent(getActivity(), HomeActivity.class);
                i.putExtra("start_profile",true);
                getActivity().finish();
                startActivity(i);

            }
        });

        binding.switchButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                SharedPrefs.getInstance(getActivity()).setNotificationStatus(b);

                if (b){
                    //Prijavi se na topic.
                    FirebaseMessaging.getInstance().subscribeToTopic("general");
                }
                else {
                    FirebaseMessaging.getInstance().unsubscribeFromTopic("general");
                }
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();

        if (DataContainer.user != null){
            updateUserUI(DataContainer.user);
        }
        else {
            String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();

            FirebaseFirestore.getInstance().collection("users").whereEqualTo("id", userId)
                    .get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                @Override
                public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                    if (queryDocumentSnapshots.getDocuments().size() == 1) {
                        User user = queryDocumentSnapshots.getDocuments().get(0).toObject(User.class);

                        updateUserUI(user);

                        DataContainer.user = user;
                    }

                }
            });
        }
    }

    private void updateUserUI(User user){
        binding.textViewMail.setText(user.email);
        binding.textViewName.setText(user.surname+" "+user.name);
        if (user.imageUrl != null && user.imageUrl.length()>0) {
            Picasso.get().load(user.imageUrl).into(binding.circleImage);
        }
    }
}