// **********************************************************************
//
// Copyright (c) 2001
// MutableRealms, Inc.
// Huntsville, AL, USA
//
// All Rights Reserved
//
// **********************************************************************

package Ice;

public class ObjectPrx
{
    public final int
    ice_hash()
    {
        return _reference.hashValue;
    }

    public final boolean
    ice_isA(String s)
    {
        int __cnt = 0;
        while (true)
        {
            try
            {
                _ObjectDel __del = __getDelegate();
                return __del.ice_isA(s);
            }
            catch (LocationForward __ex)
            {
                __locationForward(__ex);
            }
            catch (IceInternal.NonRepeatable __ex)
            {
                __cnt = __handleException(__ex.get(), __cnt);
            }
            catch (LocalException __ex)
            {
                __cnt = __handleException(__ex, __cnt);
            }
        }
    }

    public final void
    ice_ping()
    {
        int __cnt = 0;
        while (true)
        {
            try
            {
                _ObjectDel __del = __getDelegate();
                __del.ice_ping();
                return;
            }
            catch (LocationForward __ex)
            {
                __locationForward(__ex);
            }
            catch (IceInternal.NonRepeatable __ex)
            {
                __cnt = __handleException(__ex.get(), __cnt);
            }
            catch (LocalException __ex)
            {
                __cnt = __handleException(__ex, __cnt);
            }
        }
    }

    public final String
    ice_getIdentity()
    {
        return _reference.identity;
    }

    public final ObjectPrx
    ice_newIdentity(String newIdentity)
    {
        if (newIdentity.equals(_reference.identity))
        {
            return this;
        }
        else
        {
            ObjectPrx proxy = new ObjectPrx();
            proxy.setup(_reference.changeIdentity(newIdentity));
            return proxy;
        }
    }

    public final String
    ice_getFacet()
    {
        return _reference.facet;
    }

    public final ObjectPrx
    ice_newFacet(String newFacet)
    {
        if (newFacet.equals(_reference.facet))
        {
            return this;
        }
        else
        {
            ObjectPrx proxy = new ObjectPrx();
            proxy.setup(_reference.changeFacet(newFacet));
            return proxy;
        }
    }

    public final ObjectPrx
    ice_twoway()
    {
        IceInternal.Reference ref =
            _reference.changeMode(IceInternal.Reference.ModeTwoway);
        if (ref.equals(_reference))
        {
            return this;
        }
        else
        {
            ObjectPrx proxy = new ObjectPrx();
            proxy.setup(ref);
            return proxy;
        }
    }

    public final ObjectPrx
    ice_oneway()
    {
        IceInternal.Reference ref =
            _reference.changeMode(IceInternal.Reference.ModeOneway);
        if (ref.equals(_reference))
        {
            return this;
        }
        else
        {
            ObjectPrx proxy = new ObjectPrx();
            proxy.setup(ref);
            return proxy;
        }
    }

    public final ObjectPrx
    ice_batchOneway()
    {
        IceInternal.Reference ref =
            _reference.changeMode(IceInternal.Reference.ModeBatchOneway);
        if (ref.equals(_reference))
        {
            return this;
        }
        else
        {
            ObjectPrx proxy = new ObjectPrx();
            proxy.setup(ref);
            return proxy;
        }
    }

    public final ObjectPrx
    ice_datagram()
    {
        IceInternal.Reference ref =
            _reference.changeMode(IceInternal.Reference.ModeDatagram);
        if (ref.equals(_reference))
        {
            return this;
        }
        else
        {
            ObjectPrx proxy = new ObjectPrx();
            proxy.setup(ref);
            return proxy;
        }
    }

    public final ObjectPrx
    ice_batchDatagram()
    {
        IceInternal.Reference ref =
            _reference.changeMode(IceInternal.Reference.ModeBatchDatagram);
        if (ref.equals(_reference))
        {
            return this;
        }
        else
        {
            ObjectPrx proxy = new ObjectPrx();
            proxy.setup(ref);
            return proxy;
        }
    }

    public final ObjectPrx
    ice_secure(boolean b)
    {
        IceInternal.Reference ref = _reference.changeSecure(b);
        if (ref.equals(_reference))
        {
            return this;
        }
        else
        {
            ObjectPrx proxy = new ObjectPrx();
            proxy.setup(ref);
            return proxy;
        }
    }

    public final ObjectPrx
    ice_timeout(int t)
    {
        IceInternal.Reference ref = _reference.changeTimeout(t);
        if (ref.equals(_reference))
        {
            return this;
        }
        else
        {
            ObjectPrx proxy = new ObjectPrx();
            proxy.setup(ref);
            return proxy;
        }
    }

    public final void
    ice_flush()
    {
        _ObjectDel __del = __getDelegate();
        __del.ice_flush();
    }

    public final IceInternal.Reference
    __reference()
    {
        return _reference;
    }

    public final void
    __copyFrom(Ice.ObjectPrx from)
    {
        setup(from.__reference());
    }

    public final synchronized int
    __handleException(LocalException ex, int cnt)
    {
        _delegate = null;

        final int max = 1; // TODO: Make number of retries configurable

        try
        {
            throw ex;
        }
        catch (CloseConnectionException e)
        {
            //
            // We always retry on a close connection exception, as this
            // indicates graceful server shutdown.
            //
            // TODO: configurable timeout before we try again?
        }
        catch (SocketException e)
        {
            ++cnt;
        }
        catch (DNSException e)
        {
            ++cnt;
        }
        catch (TimeoutException e)
        {
            ++cnt;
        }

        IceInternal.TraceLevels traceLevels =
            _reference.instance.traceLevels();
        Logger logger = _reference.instance.logger();

        if (cnt > max)
        {
            if (traceLevels.retry >= 1)
            {
                String s = "cannot retry operation call because retry limit " +
                    "has been exceeded\n" + ex;
                logger.trace(traceLevels.retryCat, s);
            }
            throw ex;
        }

        if (traceLevels.retry >= 1)
        {
            String s = "re-trying operation call because of exception\n" + ex;
            logger.trace(traceLevels.retryCat, s);
        }

        //
        // Reset the endpoints to the original endpoints upon retry
        //
        _reference = _reference.changeEndpoints(_reference.origEndpoints);

        return cnt;
    }

    public final synchronized void
    __rethrowException(LocalException ex)
    {
        _delegate = null;

        throw ex;
    }

    public final synchronized void
    __locationForward(LocationForward ex)
    {
        _delegate = null;

        if (!_reference.identity.equals(ex._prx._reference.identity))
        {
            throw new ReferenceIdentityException();
        }

        _reference = _reference.changeEndpoints(ex._prx._reference.endpoints);

        /*
        IceInternal.TraceLevels traceLevels =
            _reference.instance.traceLevels();
        Logger logger = _reference.instance.logger();

        if (traceLevels.locationForward >= 1)
        {
            String s = "location forward for object with identity `" +
                _reference.identity + "'";
            logger.trace(traceLevels.locationForwardCat, s);
        }
        */
    }

    protected final synchronized _ObjectDel
    __getDelegate()
    {
        if (_delegate == null)
        {
            ObjectAdapter adapter = _reference.instance.objectAdapterFactory().
                findObjectAdapter(this);
            if (adapter != null)
            {
                _ObjectDelD delegate = __createDelegateD();
                delegate.setup(_reference, adapter);
                _delegate = delegate;
            }
            else
            {
                _ObjectDelM delegate = __createDelegateM();
                delegate.setup(_reference);
                _delegate = delegate;
            }
        }

        return _delegate;
    }

    protected _ObjectDelM
    __createDelegateM()
    {
        return new _ObjectDelM();
    }

    protected _ObjectDelD
    __createDelegateD()
    {
        return new _ObjectDelD();
    }

    //
    // Only for use by IceInternal.ProxyFactory
    //
    public final void
    setup(IceInternal.Reference ref)
    {
        //
        // No need to synchronize, as this operation is only called
        // upon initial initialization.
        //
        _reference = ref;
    }

    private IceInternal.Reference _reference;
    private _ObjectDel _delegate;
}
