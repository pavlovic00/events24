package com.pavlovic.cubes.events24.ui.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.Patterns;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.LinearLayoutCompat;

import com.pavlovic.cubes.events24.R;


public class EventsEditText extends LinearLayout {

    public static final int TYPE_TEXT = InputType.TYPE_CLASS_TEXT;
    public static final int TYPE_EMAIL = InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS;
    public static final int TYPE_PASSWORD_VISIBLE = InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD;
    public static final int TYPE_PASSWORD_INVISIBLE = InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD;
    public static final int TYPE_NAME = InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PERSON_NAME;

    private LinearLayout linearLayoutEditText;
    private View viewLine;
    private ImageView imageView1;
    private EditText editText;
    private ImageView imageView2;
    private final TypedArray typedArray;

    public EventsEditText(Context context) {
        super(context);
        typedArray = context.obtainStyledAttributes(R.styleable.EventsEditText);
        setupLayout();
    }

    public EventsEditText(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        typedArray = context.obtainStyledAttributes(attrs, R.styleable.EventsEditText);
        setupLayout();
    }

    public EventsEditText(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        typedArray = context.obtainStyledAttributes(attrs, R.styleable.EventsEditText);
        setupLayout();
    }

    private void setupLayout() {
        setOrientation(VERTICAL);
        setGravity(Gravity.CENTER_HORIZONTAL);
        createLinearLayoutEditText();
        createImageView1();
        createEditText();
        createImageView2();
        createViewLine();
        setupViews();
    }

    private void createLinearLayoutEditText() {

        //create EditText with images
        linearLayoutEditText = new LinearLayout(getContext());
        linearLayoutEditText.setOrientation(HORIZONTAL);
        linearLayoutEditText.setGravity(Gravity.CENTER_VERTICAL);
        linearLayoutEditText.setPadding(0, 0, 0, 32);
    }


    private void createImageView1() {
        imageView1 = new ImageView(getContext());
        imageView1.setLayoutParams(new LinearLayoutCompat.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        ));
        linearLayoutEditText.addView(imageView1);
    }

    private void createEditText() {
        editText = new EditText(getContext());
        editText.setLayoutParams(new LinearLayoutCompat.LayoutParams(
                0,
                ViewGroup.LayoutParams.WRAP_CONTENT,
                1
        ));
        editText.setGravity(Gravity.CENTER_VERTICAL);
        editText.setPadding(16, 0, 16, 0);
        editText.setBackgroundColor(getResources().getColor(R.color.transparent));
        linearLayoutEditText.addView(editText);
    }

    private void createImageView2() {
        imageView2 = new ImageView(getContext());
        imageView2.setLayoutParams(new LinearLayoutCompat.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        ));
        linearLayoutEditText.addView(imageView2);
        addView(linearLayoutEditText);
    }

    private void createViewLine() {
        viewLine = new View(getContext());
        viewLine.setLayoutParams(new LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                4
        ));
        addView(viewLine);
    }


    private void setupViews() {

        /*
        SET CUSTOM ATTRIBUTES
         */

        //SET TYPE

        int inputType = typedArray.getInt(R.styleable.EventsEditText_android_inputType, 0);

        setViewType(inputType);

        //SET TEXT

        String text = typedArray.getString(R.styleable.EventsEditText_android_text);

        if (text != null) {
            setText(text);
        }

        //SET HINT

        String hint = typedArray.getString(R.styleable.EventsEditText_android_hint);

        if (hint != null) {
            setHint(hint);
        }

        // IS ENABLED?

        boolean isEnabled = typedArray.getBoolean(R.styleable.EventsEditText_android_enabled, true);
        setViewEnabled(isEnabled);

        // SET COLOR

        int color = typedArray.getColor(R.styleable.EventsEditText_android_color, 0);
        if (color != 0) {
            setViewColor(color);
        }

    }

    public void setViewType(int inputType) {

        switch (inputType) {
            case TYPE_EMAIL:
                setViewTypeEmail(TYPE_EMAIL);
                break;
            case TYPE_PASSWORD_INVISIBLE:
                setViewTypePasswordInvisible(TYPE_PASSWORD_VISIBLE, TYPE_PASSWORD_INVISIBLE);
                break;
            case TYPE_PASSWORD_VISIBLE:
                setViewTypePasswordVisible(TYPE_PASSWORD_VISIBLE, TYPE_PASSWORD_INVISIBLE);
                break;
            case TYPE_NAME:
                setViewTypeName(TYPE_NAME);
                break;
            default:
                setViewTypeText(TYPE_TEXT);
                break;
        }
    }


    private void setViewTypeEmail(int inputTypeEmail) {
        imageView1.setImageResource(R.drawable.ic_mail);
        editText.setInputType(inputTypeEmail);
        editText.setHint("E-mail");
        imageView2.setImageResource(R.drawable.ic_check);

        //TextChangedListener

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (isValidEmail(s)) {
                    imageView2.setColorFilter(getResources().getColor(R.color.green));
                } else {
                    imageView2.setColorFilter(getResources().getColor(R.color.red));
                }
            }
        });
    }

    private static boolean isValidEmail(CharSequence target) {
        return (!TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches());
    }

    private void setViewTypePasswordInvisible(int inputTypePasswordVisible, int inputTypePasswordInvisible) {
        imageView1.setImageResource(R.drawable.ic_lock);
        editText.setInputType(inputTypePasswordInvisible);
        editText.setHint("Password");
        imageView2.setImageResource(R.drawable.ic_visibility_off);
        imageView2.setOnClickListener(view -> {
            if (editText.getInputType() == inputTypePasswordInvisible) {
                editText.setInputType(inputTypePasswordVisible);
                imageView2.setImageResource(R.drawable.ic_visibility);
            } else {
                editText.setInputType(inputTypePasswordInvisible);
                imageView2.setImageResource(R.drawable.ic_visibility_off);
            }
        });
    }

    private void setViewTypePasswordVisible(int inputTypePasswordVisible, int inputTypePasswordInvisible) {
        imageView1.setImageResource(R.drawable.ic_lock);
        editText.setInputType(inputTypePasswordVisible);
        editText.setHint("Password");
        imageView2.setImageResource(R.drawable.ic_visibility);
        imageView2.setOnClickListener(view -> {
            if (editText.getInputType() == inputTypePasswordInvisible) {
                editText.setInputType(inputTypePasswordVisible);
                imageView2.setImageResource(R.drawable.ic_visibility);
            } else {
                editText.setInputType(inputTypePasswordInvisible);
                imageView2.setImageResource(R.drawable.ic_visibility_off);
            }
        });
    }

    private void setViewTypeName(int inputTypeName) {
        editText.setInputType(inputTypeName);
        imageView1.setImageResource(R.drawable.ic_person);
        editText.setHint("Name");
        imageView2.setVisibility(GONE);
    }

    // RANDOM TYPE
    private void setViewTypeText(int inputTypeText) {
        int image = typedArray.getResourceId(R.styleable.EventsEditText_android_src, 0);
        imageView1.setImageResource(image);
        editText.setInputType(inputTypeText);
    }


    public Editable getText() {
        return editText.getText();
    }

    public void setText(String text) {
        editText.setText(text);
    }

    public void setHint(String hint) {
        editText.setHint(hint);
    }

    public void setImageResource(int image) {
        imageView1.setImageResource(image);
    }

    public void setViewEnabled(boolean isEnabled) {
        imageView1.setEnabled(isEnabled);
        editText.setEnabled(isEnabled);
        imageView2.setEnabled(isEnabled);
        if (isEnabled) {
            setViewColor(getResources().getColor(R.color.white));
        } else {
            setViewColor(getResources().getColor(R.color.purple_light));
        }
    }


    public void setViewColor(int color) {

        imageView1.setColorFilter(color);
        imageView2.setColorFilter(color);
        viewLine.setBackgroundColor(color);
        editText.setTextColor(color);
        editText.setHintTextColor(getDarkerColorVersion(color, 0.7f));
    }

    private int getDarkerColorVersion(int color, float factor) {
        int a = Color.alpha( color );
        int r = Color.red( color );
        int g = Color.green( color );
        int b = Color.blue( color );

        return Color.argb( a,
                Math.max( (int)(r * factor), 0 ),
                Math.max( (int)(g * factor), 0 ),
                Math.max( (int)(b * factor), 0 ) );
    }

}
