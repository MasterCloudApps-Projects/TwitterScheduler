package com.mastercloudapps.twitterscheduler.application.usecase;

import com.mastercloudapps.twitterscheduler.application.model.operation.DeletePendingTweetOperation;

public interface DeletePendingTweetUseCase {

	public void delete(DeletePendingTweetOperation operation);
}
