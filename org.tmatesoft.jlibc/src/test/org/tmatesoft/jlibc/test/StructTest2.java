package org.tmatesoft.jlibc.test;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.tmatesoft.jlibc.Memory;
import org.tmatesoft.jlibc.Struct;
import org.tmatesoft.jlibc.Memory.Pointer;

public class StructTest2 {

	private final Memory memory = new Memory.ByteArray(1024);
	private final Pointer pointer = new Pointer(memory, 0);
	private TestStruct struct = new TestStruct(pointer);

	@Before
	public void setUp() {
		struct.f1().set((byte) 1);
		struct.s1().f1().set((byte) 2);
		struct.s1().f2().set((byte) 3);
		struct.f2().set((byte) 4);
		struct.s2().f1().set((byte) 5);
		struct.s2().f2().set((byte) 6);
	}

	@Test
	public void testOffset() {
		assertEquals(0, struct.f1().offset());
		assertEquals(1, struct.s1().f1().offset());
		assertEquals(2, struct.s1().f2().offset());
		assertEquals(6, struct.f2().offset());
		assertEquals(8, struct.s2().f1().offset());
		assertEquals(9, struct.s2().f2().offset());
	}

	@Test
	public void testSet() {
		assertEquals(1, memory.getByte(0));
		assertEquals(2, memory.getByte(1));
		assertEquals(3, memory.getByte(5));
		assertEquals(4, memory.getByte(7));
		assertEquals(5, memory.getByte(8));
		assertEquals(6, memory.getByte(12));
	}

	@Test
	public void testSetGet() {
		assertEquals(1, struct.f1().get());
		assertEquals(2, struct.s1().f1().get());
		assertEquals(3, struct.s1().f2().get());
		assertEquals(4, struct.f2().get());
		assertEquals(5, struct.s2().f1().get());
		assertEquals(6, struct.s2().f2().get());
	}

	static class TestStruct extends Struct {

		public TestStruct(final Pointer pointer) {
			super(pointer);
		}

		public static class TestSubStruct extends Struct {

			private UnsignedByte f1 = new UnsignedByte(this);
			private UnsignedInt f2 = new UnsignedInt(this);

			public TestSubStruct(final TestStruct parent) {
				super(parent);
			}

			public UnsignedByte f1() {
				return f1;
			}

			public UnsignedInt f2() {
				return f2;
			}
		}

		private UnsignedByte f1 = new UnsignedByte(this);

		private TestSubStruct s1 = new TestSubStruct(this);

		private UnsignedShort f2 = new UnsignedShort(this);

		private TestSubStruct s2 = new TestSubStruct(this);

		public UnsignedByte f1() {
			return f1;
		}

		public TestSubStruct s1() {
			return s1;
		}

		public UnsignedShort f2() {
			return f2;
		}

		public TestSubStruct s2() {
			return s2;
		}

	}

}
