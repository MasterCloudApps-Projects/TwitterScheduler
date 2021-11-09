package com.mastercloudapps.twitterscheduler.domain.shared;

import java.io.Serializable;

public interface Identifiable<I extends Serializable> extends Serializable {

	I id();
}
