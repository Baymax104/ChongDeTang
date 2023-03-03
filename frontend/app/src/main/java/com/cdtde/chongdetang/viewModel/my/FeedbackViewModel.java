package com.cdtde.chongdetang.viewModel.my;

import androidx.lifecycle.ViewModel;

import com.cdtde.chongdetang.repository.MyRepository;


public class FeedbackViewModel extends ViewModel {

    private final MyRepository repo;
    private String feedbackContent;

    public FeedbackViewModel() {
        repo = MyRepository.getInstance();


    }

    public String getFeedbackContent() {
        return feedbackContent;
    }

    public void setFeedbackContent(String feedbackContent) {
        this.feedbackContent = feedbackContent;
    }

    public void updateFeedback() {
        repo.requestFeedbackCommit(feedbackContent);
    }
}
