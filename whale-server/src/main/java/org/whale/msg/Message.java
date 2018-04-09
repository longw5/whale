package org.whale.msg;

import java.io.Serializable;

import io.netty.util.ReferenceCounted;

public class Message implements Serializable {

	private static final long serialVersionUID = 1164568698876645464L;

	private final Object msg;

    public Message(final Object msg) {
        this.msg = msg;
    }

    public Object getMsg() {
        return msg;
    }

    public void tryRelease() {
        if (msg instanceof ReferenceCounted)
            ((ReferenceCounted) msg).release();
    }
}
