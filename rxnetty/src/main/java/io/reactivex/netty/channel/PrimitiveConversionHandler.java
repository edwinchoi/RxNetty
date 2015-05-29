/*
 * Copyright 2015 Netflix, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.reactivex.netty.channel;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOutboundHandlerAdapter;
import io.netty.channel.ChannelPromise;

/**
 * A {@link ChannelHandler} to support directly writing primitives like String and byte arrays on a {@link Connection}.
 */
@Sharable
public class PrimitiveConversionHandler extends ChannelOutboundHandlerAdapter {

    public static final PrimitiveConversionHandler INSTANCE = new PrimitiveConversionHandler();

    PrimitiveConversionHandler() {
    }

    @Override
    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
        Object msgToWrite = msg;

        if (msg instanceof String) {
            msgToWrite = ctx.alloc().buffer().writeBytes(((String) msg).getBytes());
        } else if (msg instanceof byte[]) {
            msgToWrite = ctx.alloc().buffer().writeBytes((byte[]) msg);
        }

        super.write(ctx, msgToWrite, promise);
    }
}
