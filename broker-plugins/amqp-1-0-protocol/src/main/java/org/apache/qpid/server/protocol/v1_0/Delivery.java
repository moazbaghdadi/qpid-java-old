/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package org.apache.qpid.server.protocol.v1_0;

import org.apache.qpid.server.protocol.v1_0.type.Binary;
import org.apache.qpid.server.protocol.v1_0.type.UnsignedInteger;
import org.apache.qpid.server.protocol.v1_0.type.transport.Transfer;

public class Delivery
{
    private final UnsignedInteger _deliveryId;
    private final Binary _deliveryTag;
    private final LinkEndpoint _linkEndpoint;
    private boolean _complete;
    private boolean _settled;
    private int _numberOfTransfers = 0;

    public Delivery(Transfer transfer, final LinkEndpoint endpoint)
    {
        _settled = Boolean.TRUE.equals(transfer.getSettled());
        _deliveryId = transfer.getDeliveryId();
        _deliveryTag = transfer.getDeliveryTag();
        _linkEndpoint = endpoint;
        addTransfer(transfer);
    }

    public boolean isComplete()
    {
        return _complete;
    }

    public void setComplete(final boolean complete)
    {
        _complete = complete;
    }

    public boolean isSettled()
    {
        return _settled;
    }

    public void setSettled(final boolean settled)
    {
        _settled = settled;
    }

    public final void addTransfer(Transfer transfer)
    {
        _numberOfTransfers++;
        if(Boolean.TRUE.equals(transfer.getAborted()) || !Boolean.TRUE.equals(transfer.getMore()))
        {
            setComplete(true);
        }
        if(Boolean.TRUE.equals(transfer.getSettled()))
        {
            setSettled(true);
        }
    }

    public UnsignedInteger getDeliveryId()
    {
        return _deliveryId;
    }

    public LinkEndpoint getLinkEndpoint()
    {
        return _linkEndpoint;
    }

    public Binary getDeliveryTag()
    {
        return _deliveryTag;
    }

    public int getNumberOfTransfers()
    {
        return _numberOfTransfers;
    }
}
