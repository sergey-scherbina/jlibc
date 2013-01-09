package org.tmatesoft.jlibc;

import org.tmatesoft.jlibc.Memory.Pointer;

public abstract class Struct {

	private final Struct parent;
	private final Pointer pointer;
	private int size = 0;

	public Struct(final Pointer pointer) {
		this.pointer = pointer;
		this.parent = null;
	}

	public Struct(final Struct parent) {
		this.pointer = parent.pointer();
		this.parent = parent;
		parent.add(size);
	}

	public Pointer pointer() {
		return pointer;
	}

	public Struct parent() {
		return parent;
	}

	public int size() {
		return size;
	}

	private void add(final int size) {
		this.size += size;
		if (parent != null) {
			parent.add(size);
		}
	}

	private int offset() {
		if (parent != null) {
			return parent.size;
		} else {
			return size;
		}
	}

	private static class Field {

		private final Struct struct;
		private final int size;
		private final int offset;

		public Field(final Struct struct, final int size) {
			this.struct = struct;
			this.size = size;
			this.offset = struct.offset();
			struct.add(size);
		}

		public Struct struct() {
			return struct;
		}

		public int offset() {
			return offset;
		}

		public int size() {
			return size;
		}

	}

	public static class SignedByte extends Field {
		public SignedByte(final Struct struct) {
			super(struct, 1);
		}

		public byte get() {
			return struct().pointer().getByte(offset());
		}

		public void set(final byte value) {
			struct().pointer().setByte(offset(), value);
		}
	}

	public static class SignedShort extends Field {
		public SignedShort(final Struct struct) {
			super(struct, 2);
		}

		public short get() {
			return struct().pointer().getShort(offset());
		}

		public void set(final short value) {
			struct().pointer().setShort(offset(), value);
		}
	}

	public static class SignedInt extends Field {
		public SignedInt(final Struct struct) {
			super(struct, 4);
		}

		public int get() {
			return struct().pointer().getInt(offset());
		}

		public void set(final int value) {
			struct().pointer().setInt(offset(), value);
		}
	}

	public static class SignedLong extends Field {
		public SignedLong(final Struct struct) {
			super(struct, 4);
		}

		public long get() {
			return struct().pointer().getLong(offset());
		}

		public void set(final long value) {
			struct().pointer().setLong(offset(), value);
		}
	}

	public static class UnsignedByte extends Field {
		public UnsignedByte(final Struct struct) {
			super(struct, 1);
		}

		public short get() {
			return struct().pointer().getUnsignedByte(offset());
		}

		public void set(final short value) {
			struct().pointer().setUnsignedByte(offset(), value);
		}
	}

	public static class UnsignedShort extends Field {
		public UnsignedShort(final Struct struct) {
			super(struct, 2);
		}

		public int get() {
			return struct().pointer().getUnsignedShort(offset());
		}

		public void set(final int value) {
			struct().pointer().setUnsignedShort(offset(), value);
		}
	}

	public static class UnsignedInt extends Field {
		public UnsignedInt(final Struct struct) {
			super(struct, 4);
		}

		public long get() {
			return struct().pointer().getUnsignedInt(offset());
		}

		public void set(final long value) {
			struct().pointer().setUnsignedInt(offset(), value);
		}
	}

}
