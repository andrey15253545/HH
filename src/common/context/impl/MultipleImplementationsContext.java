package common.context.impl;

import common.storage.Storage;

public class MultipleImplementationsContext extends EasyContext {

    public MultipleImplementationsContext(Storage storage) {
        super.storage = storage;
    }

}
