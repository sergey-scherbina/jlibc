package org.tmatesoft.jlibc;

public interface Memory {

	byte get(int address);

	void set(int address, byte value);

	Pointer pointer(int address);

	class Pointer {

		private final Memory memory;
		private int address;

		public Pointer(final Memory memory, final int address) {
			this.memory = memory;
			this.address = address;
		}

		public Pointer pointer(final int offset) {
			return memory.pointer(address + offset);
		}

		public int address() {
			return address;
		}

		public void move(final int offset) {
			address += offset;
		}

		public void move(final Pointer pointer) {
			address += pointer.address;
		}

		public byte get(final int offset) {
			return memory.get(address + offset);
		}

		public void set(final int offset, final byte value) {
			memory.set(address + offset, value);
		}

	}

	class ByteArray implements Memory {

		private final byte[] bytes;

		public ByteArray(final byte[] bytes) {
			this.bytes = bytes;
		}

		public ByteArray(int size) {
			this(new byte[size]);
		}

		@Override
		public byte get(final int address) {
			return bytes[address];
		}

		@Override
		public void set(final int address, final byte value) {
			bytes[address] = value;
		}

		@Override
		public Pointer pointer(final int address) {
			return new Pointer(this, address);
		}
	}

}
