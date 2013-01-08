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

		public void set(byte value) {
			struct().pointer().setByte(offset(), value);
		}
	}

}
