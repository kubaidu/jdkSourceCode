/*
 * Copyright (c) 2000, 2011, Oracle and/or its affiliates. All rights reserved.
 * ORACLE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

// -- This file was mechanically generated: Do not edit! -- //

package java.nio;

import sun.misc.Cleaner;
import sun.misc.Unsafe;
import sun.nio.ch.DirectBuffer;
import sun.nio.ch.FileChannelImpl;


class DirectShortBufferU

    extends ShortBuffer



    implements DirectBuffer
{



    // Cached unsafe-access object
    protected static final Unsafe unsafe = Bits.unsafe();

    // Cached array base offset
    private static final long arrayBaseOffset = (long)unsafe.arrayBaseOffset(short[].class);

    // Cached unaligned-access capability
    protected static final boolean unaligned = Bits.unaligned();

    // Base address, used in all indexing calculations
    // NOTE: moved up to Buffer.java for speed in JNI GetDirectBufferAddress
    //    protected long address;

    // An object attached to this buffer. If this buffer is a view of another
    // buffer then we use this field to keep a reference to that buffer to
    // ensure that its memory isn't freed before we are done with it.
    private final Object viewedBuffer;

    public Object viewedBuffer() {
        return viewedBuffer;
    }




































    public Cleaner cleaner() { return null; }









































































    // For duplicates and slices
    //
    DirectShortBufferU(DirectBuffer db,	        // package-private
			       int mark, int pos, int lim, int cap,
			       int off)
    {

	super(mark, pos, lim, cap);
	address = db.address() + off;



        viewedBuffer = db;



    }

    public ShortBuffer slice() {
	int pos = this.position();
	int lim = this.limit();
	assert (pos <= lim);
	int rem = (pos <= lim ? lim - pos : 0);
	int off = (pos << 1);
        assert (off >= 0);
	return new DirectShortBufferU(this, -1, 0, rem, rem, off);
    }

    public ShortBuffer duplicate() {
	return new DirectShortBufferU(this,
					      this.markValue(),
					      this.position(),
					      this.limit(),
					      this.capacity(),
					      0);
    }

    public ShortBuffer asReadOnlyBuffer() {

	return new DirectShortBufferRU(this,
					   this.markValue(),
					   this.position(),
					   this.limit(),
					   this.capacity(),
					   0);



    }



    public long address() {
	return address;
    }

    private long ix(int i) {
        return address + (i << 1);
    }

    public short get() {
	return ((unsafe.getShort(ix(nextGetIndex()))));
    }

    public short get(int i) {
	return ((unsafe.getShort(ix(checkIndex(i)))));
    }

    public ShortBuffer get(short[] dst, int offset, int length) {

	if ((length << 1) > Bits.JNI_COPY_TO_ARRAY_THRESHOLD) {
	    checkBounds(offset, length, dst.length);
	    int pos = position();
	    int lim = limit();
	    assert (pos <= lim);
	    int rem = (pos <= lim ? lim - pos : 0);
	    if (length > rem)
		throw new BufferUnderflowException();


            if (order() != ByteOrder.nativeOrder())
                Bits.copyToShortArray(ix(pos), dst,
                                          offset << 1,
                                          length << 1);
            else

                Bits.copyToArray(ix(pos), dst, arrayBaseOffset,
                                 offset << 1,
                                 length << 1);
            position(pos + length);
        } else {
            super.get(dst, offset, length);
        }
	return this;



    }



    public ShortBuffer put(short x) {

	unsafe.putShort(ix(nextPutIndex()), ((x)));
	return this;



    }

    public ShortBuffer put(int i, short x) {

	unsafe.putShort(ix(checkIndex(i)), ((x)));
	return this;



    }

    public ShortBuffer put(ShortBuffer src) {

	if (src instanceof DirectShortBufferU) {
	    if (src == this)
		throw new IllegalArgumentException();
	    DirectShortBufferU sb = (DirectShortBufferU)src;

	    int spos = sb.position();
	    int slim = sb.limit();
	    assert (spos <= slim);
	    int srem = (spos <= slim ? slim - spos : 0);

	    int pos = position();
	    int lim = limit();
	    assert (pos <= lim);
	    int rem = (pos <= lim ? lim - pos : 0);

	    if (srem > rem)
		throw new BufferOverflowException();
 	    unsafe.copyMemory(sb.ix(spos), ix(pos), srem << 1);
 	    sb.position(spos + srem);
 	    position(pos + srem);
	} else if (src.hb != null) {

	    int spos = src.position();
	    int slim = src.limit();
	    assert (spos <= slim);
	    int srem = (spos <= slim ? slim - spos : 0);

	    put(src.hb, src.offset + spos, srem);
	    src.position(spos + srem);

	} else {
	    super.put(src);
	}
	return this;



    }

    public ShortBuffer put(short[] src, int offset, int length) {

	if ((length << 1) > Bits.JNI_COPY_FROM_ARRAY_THRESHOLD) {
	    checkBounds(offset, length, src.length);
	    int pos = position();
	    int lim = limit();
	    assert (pos <= lim);
	    int rem = (pos <= lim ? lim - pos : 0);
	    if (length > rem)
		throw new BufferOverflowException();


            if (order() != ByteOrder.nativeOrder())
                Bits.copyFromShortArray(src, offset << 1,
                                            ix(pos), length << 1);
            else

                Bits.copyFromArray(src, arrayBaseOffset, offset << 1,
                                   ix(pos), length << 1);
            position(pos + length);
        } else {
            super.put(src, offset, length);
        }
	return this;



    }
    
    public ShortBuffer compact() {

	int pos = position();
	int lim = limit();
	assert (pos <= lim);
	int rem = (pos <= lim ? lim - pos : 0);

 	unsafe.copyMemory(ix(pos), ix(0), rem << 1);
 	position(rem);
	limit(capacity());
	return this;



    }

    public boolean isDirect() {
	return true;
    }

    public boolean isReadOnly() {
	return false;
    }















































    public ByteOrder order() {





	return ((ByteOrder.nativeOrder() != ByteOrder.BIG_ENDIAN)
		? ByteOrder.LITTLE_ENDIAN : ByteOrder.BIG_ENDIAN);

    }


























}
