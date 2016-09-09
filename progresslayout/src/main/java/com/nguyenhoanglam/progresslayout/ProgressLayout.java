package com.nguyenhoanglam.progresslayout;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ProgressLayout extends RelativeLayout {

    private static final String TAG_LOADING = "ProgressView.TAG_LOADING";
    private static final String TAG_EMPTY = "ProgressView.TAG_EMPTY";
    private static final String TAG_ERROR = "ProgressView.TAG_ERROR";

    final String CONTENT = "type_content";
    final String LOADING = "type_loading";
    final String EMPTY = "type_empty";
    final String ERROR = "type_error";

    LayoutInflater inflater;
    View view;
    LayoutParams layoutParams;
    Drawable currentBackground;

    List<View> contentViews = new ArrayList<>();

    RelativeLayout loadingStateRelativeLayout;
    ProgressWheel loadingStateProgressBar;

    RelativeLayout emptyStateRelativeLayout;
    ImageView emptyStateImageView;
    TextView emptyStateContentTextView;

    RelativeLayout errorStateRelativeLayout;
    ImageView errorStateImageView;
    TextView errorStateContentTextView;
    Button errorStateButton;

    int loadingStateProgressBarRadius;
    int loadingStateBackgroundColor;
    int loadingStateProgressBarColor;
    int loadingStateProgressBarSpinWidth;

    int emptyStateImageWidth;
    int emptyStateImageHeight;
    int emptyStateContentTextSize;
    int emptyStateContentTextColor;
    int emptyStateBackgroundColor;

    int errorStateImageWidth;
    int errorStateImageHeight;
    int errorStateContentTextSize;
    int errorStateButtonTextSize;
    int errorStateContentTextColor;
    int errorStateButtonTextColor;
    int errorStateBackgroundColor;

    private String state = CONTENT;

    public ProgressLayout(Context context) {
        super(context);
    }

    public ProgressLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public ProgressLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    private void init(AttributeSet attrs) {
        inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.ProgressLayout);

        Resources res = getResources();
        float scaleRatio = res.getDisplayMetrics().density;

        // Get default dimensions
        loadingStateProgressBarRadius = (int) res.getDimension(R.dimen.progressbar_radius);
        loadingStateProgressBarSpinWidth = (int) res.getDimension(R.dimen.spin_width);
        emptyStateImageWidth = emptyStateImageHeight = errorStateImageWidth = errorStateImageHeight
                = (int) res.getDimension(R.dimen.image_size);
        emptyStateContentTextSize = errorStateContentTextSize = errorStateButtonTextSize
                = (int) res.getDimension(R.dimen.font_size);

        // Loading state attrs
        loadingStateProgressBarRadius = (int) (typedArray.getDimension(R.styleable.ProgressLayout_loadingProgressBarRadius, loadingStateProgressBarRadius) / scaleRatio);
        loadingStateProgressBarSpinWidth = (int) (typedArray.getDimension(R.styleable.ProgressLayout_loadingProgressBarSpinWidth, loadingStateProgressBarSpinWidth) / scaleRatio);
        loadingStateBackgroundColor = typedArray.getColor(R.styleable.ProgressLayout_loadingBackgroundColor, Color.TRANSPARENT);
        loadingStateProgressBarColor = typedArray.getColor(R.styleable.ProgressLayout_loadingProgressBarColor, Color.TRANSPARENT);

        // Empty state attrs
        emptyStateImageWidth = (int) (typedArray.getDimension(R.styleable.ProgressLayout_emptyImageWidth, emptyStateImageWidth) / scaleRatio);
        emptyStateImageHeight = (int) (typedArray.getDimension(R.styleable.ProgressLayout_emptyImageHeight, emptyStateImageHeight) / scaleRatio);
        emptyStateContentTextSize = (int) (typedArray.getDimension(R.styleable.ProgressLayout_emptyContentTextSize, emptyStateContentTextSize) / scaleRatio);
        emptyStateContentTextColor = typedArray.getColor(R.styleable.ProgressLayout_emptyContentTextColor, ContextCompat.getColor(getContext(), R.color.grey));
        emptyStateBackgroundColor = typedArray.getColor(R.styleable.ProgressLayout_emptyBackgroundColor, Color.TRANSPARENT);

        // Error state attrs
        errorStateImageWidth = (int) (typedArray.getDimension(R.styleable.ProgressLayout_errorImageWidth, errorStateImageWidth) / scaleRatio);
        errorStateImageHeight = (int) (typedArray.getDimension(R.styleable.ProgressLayout_errorImageHeight, errorStateImageHeight) / scaleRatio);
        errorStateContentTextSize = (int) (typedArray.getDimension(R.styleable.ProgressLayout_errorContentTextSize, errorStateContentTextSize) / scaleRatio);
        errorStateButtonTextSize = (int) (typedArray.getDimension(R.styleable.ProgressLayout_errorButtonTextSize, errorStateButtonTextSize) / scaleRatio);
        errorStateContentTextColor = typedArray.getColor(R.styleable.ProgressLayout_errorContentTextColor, ContextCompat.getColor(getContext(), R.color.grey));
        errorStateButtonTextColor = typedArray.getColor(R.styleable.ProgressLayout_errorButtonTextColor, ContextCompat.getColor(getContext(), R.color.teal));
        errorStateBackgroundColor = typedArray.getColor(R.styleable.ProgressLayout_errorBackgroundColor, Color.TRANSPARENT);

        typedArray.recycle();

        currentBackground = this.getBackground();
    }

    @Override
    public void addView(View child, int index, ViewGroup.LayoutParams params) {
        super.addView(child, index, params);
        if (child.getTag() == null || (!child.getTag().equals(TAG_LOADING)
                && !child.getTag().equals(TAG_EMPTY) && !child.getTag().equals(TAG_ERROR))) {
            contentViews.add(child);
        }
    }

    /**
     * Hide all other states and show content
     */
    public void showContent() {
        switchState(CONTENT, null, null, null, null, Collections.<Integer>emptyList());
    }

    /**
     * Hide all other states and show content
     *
     * @param skipIds Ids of views not to show
     */
    public void showContent(List<Integer> skipIds) {
        switchState(CONTENT, null, null, null, null, skipIds);
    }

    /**
     * Hide content and show the progress bar
     */
    public void showLoading() {
        switchState(LOADING, null, null, null, null, Collections.<Integer>emptyList());
    }

    /**
     * Hide content and show the progress bar
     *
     * @param skipIds Ids of views to not hide
     */
    public void showLoading(List<Integer> skipIds) {
        switchState(LOADING, null, null, null, null, skipIds);
    }

    /**
     * Show empty view when there are not data to show
     *
     * @param emptyImageDrawable Drawable to show
     * @param emptyTextContent   Content of the empty view to show
     */
    public void showEmpty(Drawable emptyImageDrawable, String emptyTextContent) {
        switchState(EMPTY, emptyImageDrawable, emptyTextContent, null, null, Collections.<Integer>emptyList());
    }

    /**
     * Show empty view when there are not data to show
     *
     * @param emptyImageDrawable Drawable to show
     * @param emptyTextContent   Content of the empty view to show
     * @param skipIds            Ids of views to not hide
     */
    public void showEmpty(Drawable emptyImageDrawable, String emptyTextContent, List<Integer> skipIds) {
        switchState(EMPTY, emptyImageDrawable, emptyTextContent, null, null, skipIds);
    }

    /**
     * Show error view with a button when something goes wrong and prompting the user to try again
     *
     * @param errorImageDrawable Drawable to show
     * @param errorTextContent   Content of the error view to show
     * @param errorButtonText    Text on the error view button to show
     * @param onClickListener    Listener of the error view button
     */
    public void showError(Drawable errorImageDrawable, String errorTextContent, String errorButtonText, OnClickListener onClickListener) {
        switchState(ERROR, errorImageDrawable, errorTextContent, errorButtonText, onClickListener, Collections.<Integer>emptyList());
    }

    /**
     * Show error view with a button when something goes wrong and prompting the user to try again
     *
     * @param errorImageDrawable Drawable to show
     * @param errorTextContent   Content of the error view to show
     * @param errorButtonText    Text on the error view button to show
     * @param onClickListener    Listener of the error view button
     * @param skipIds            Ids of views to not hide
     */
    public void showError(Drawable errorImageDrawable, String errorTextContent, String errorButtonText, OnClickListener onClickListener, List<Integer> skipIds) {
        switchState(ERROR, errorImageDrawable, errorTextContent, errorButtonText, onClickListener, skipIds);
    }

    public String getState() {
        return state;
    }

    public boolean isContent() {
        return state.equals(CONTENT);
    }

    public boolean isLoading() {
        return state.equals(LOADING);
    }

    public boolean isEmpty() {
        return state.equals(EMPTY);
    }

    public boolean isError() {
        return state.equals(ERROR);
    }

    private void switchState(String state, Drawable drawable, String errorTextContent,
                             String errorButtonText, OnClickListener onClickListener, List<Integer> skipIds) {
        this.state = state;

        switch (state) {
            case CONTENT:
                //Hide all state views to display content
                hideLoadingView();
                hideEmptyView();
                hideErrorView();

                setContentVisibility(true, skipIds);
                break;
            case LOADING:
                hideEmptyView();
                hideErrorView();

                setLoadingView();
                setContentVisibility(false, skipIds);
                break;
            case EMPTY:
                hideLoadingView();
                hideErrorView();

                setEmptyView();
                if (drawable != null) {
                    emptyStateImageView.setImageDrawable(drawable);
                    emptyStateImageView.setVisibility(View.VISIBLE);
                } else {
                    emptyStateImageView.setVisibility(View.GONE);
                }
                emptyStateContentTextView.setText(errorTextContent);
                setContentVisibility(false, skipIds);
                break;
            case ERROR:
                hideLoadingView();
                hideEmptyView();

                setErrorView();

                if (drawable != null) {
                    errorStateImageView.setImageDrawable(drawable);
                    errorStateImageView.setVisibility(View.VISIBLE);
                } else {
                    errorStateImageView.setVisibility(View.GONE);
                }
                errorStateContentTextView.setText(errorTextContent);
                errorStateButton.setText(errorButtonText);
                errorStateButton.setOnClickListener(onClickListener);
                setContentVisibility(false, skipIds);
                break;
        }
    }

    private void setLoadingView() {
        if (loadingStateRelativeLayout == null) {
            view = inflater.inflate(R.layout.progress_loading_view, null);
            loadingStateRelativeLayout = (RelativeLayout) view.findViewById(R.id.loadingStateRelativeLayout);
            loadingStateRelativeLayout.setTag(TAG_LOADING);

            loadingStateProgressBar = (ProgressWheel) view.findViewById(R.id.loadingStateProgressBar);

            loadingStateProgressBar.setCircleRadius(loadingStateProgressBarRadius);
            loadingStateProgressBar.setBarWidth(loadingStateProgressBarSpinWidth);

            if (loadingStateProgressBarColor != Color.TRANSPARENT) {
                loadingStateProgressBar.setBarColor(loadingStateProgressBarColor);
            }

            loadingStateProgressBar.requestLayout();

            //Set background color if not TRANSPARENT
            if (loadingStateBackgroundColor != Color.TRANSPARENT) {
                this.setBackgroundColor(loadingStateBackgroundColor);
            }

            layoutParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT);
            layoutParams.addRule(CENTER_IN_PARENT);

            addView(loadingStateRelativeLayout, layoutParams);
        } else {
            loadingStateRelativeLayout.setVisibility(VISIBLE);
        }
    }

    private void setEmptyView() {
        if (emptyStateRelativeLayout == null) {
            view = inflater.inflate(R.layout.progress_empty_view, null);
            emptyStateRelativeLayout = (RelativeLayout) view.findViewById(R.id.emptyStateRelativeLayout);
            emptyStateRelativeLayout.setTag(TAG_EMPTY);

            emptyStateImageView = (ImageView) view.findViewById(R.id.emptyStateImageView);
            emptyStateContentTextView = (TextView) view.findViewById(R.id.emptyStateContentTextView);

            //Set empty state image width and height
            emptyStateImageView.getLayoutParams().width = emptyStateImageWidth;
            emptyStateImageView.getLayoutParams().height = emptyStateImageHeight;
            emptyStateImageView.requestLayout();

            emptyStateContentTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, emptyStateContentTextSize);
            emptyStateContentTextView.setTextColor(emptyStateContentTextColor);

            //Set background color if not TRANSPARENT
            if (emptyStateBackgroundColor != Color.TRANSPARENT) {
                this.setBackgroundColor(emptyStateBackgroundColor);
            }

            layoutParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT);
            layoutParams.addRule(CENTER_IN_PARENT);

            addView(emptyStateRelativeLayout, layoutParams);
        } else {
            emptyStateRelativeLayout.setVisibility(VISIBLE);
        }
    }

    private void setErrorView() {
        if (errorStateRelativeLayout == null) {
            view = inflater.inflate(R.layout.progress_error_view, null);
            errorStateRelativeLayout = (RelativeLayout) view.findViewById(R.id.errorStateRelativeLayout);
            errorStateRelativeLayout.setTag(TAG_ERROR);

            errorStateImageView = (ImageView) view.findViewById(R.id.errorStateImageView);
            errorStateContentTextView = (TextView) view.findViewById(R.id.errorStateContentTextView);
            errorStateButton = (Button) view.findViewById(R.id.errorStateButton);

            //Set error state image width and height
            errorStateImageView.getLayoutParams().width = errorStateImageWidth;
            errorStateImageView.getLayoutParams().height = errorStateImageHeight;
            errorStateImageView.requestLayout();

            errorStateContentTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, errorStateContentTextSize);
            errorStateContentTextView.setTextColor(errorStateContentTextColor);

            errorStateButton.setTextSize(TypedValue.COMPLEX_UNIT_SP, errorStateButtonTextSize);
            errorStateButton.setTextColor(errorStateButtonTextColor);

            //Set background color if not TRANSPARENT
            if (errorStateBackgroundColor != Color.TRANSPARENT) {
                this.setBackgroundColor(errorStateBackgroundColor);
            }

            layoutParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT);
            layoutParams.addRule(CENTER_IN_PARENT);

            addView(errorStateRelativeLayout, layoutParams);
        } else {
            errorStateRelativeLayout.setVisibility(VISIBLE);
        }
    }

    private void setContentVisibility(boolean visible, List<Integer> skipIds) {
        for (View v : contentViews) {
            if (!skipIds.contains(v.getId())) {
                v.setVisibility(visible ? View.VISIBLE : View.GONE);
            }
        }
    }

    private void hideLoadingView() {
        if (loadingStateRelativeLayout != null) {
            loadingStateRelativeLayout.setVisibility(GONE);

            //Restore the background color if not TRANSPARENT
            if (loadingStateBackgroundColor != Color.TRANSPARENT) {
                this.setBackgroundDrawable(currentBackground);
            }
        }
    }

    private void hideEmptyView() {
        if (emptyStateRelativeLayout != null) {
            emptyStateRelativeLayout.setVisibility(GONE);

            //Restore the background color if not TRANSPARENT
            if (emptyStateBackgroundColor != Color.TRANSPARENT) {
                this.setBackgroundDrawable(currentBackground);
            }
        }
    }

    private void hideErrorView() {
        if (errorStateRelativeLayout != null) {
            errorStateRelativeLayout.setVisibility(GONE);

            //Restore the background color if not TRANSPARENT
            if (errorStateBackgroundColor != Color.TRANSPARENT) {
                this.setBackgroundDrawable(currentBackground);
            }

        }
    }


}
