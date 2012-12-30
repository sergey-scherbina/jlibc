package org.tmatesoft.jlibc.test;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.tmatesoft.jlibc.Memory;
import org.tmatesoft.jlibc.Struct;
import org.tmatesoft.jlibc.Memory.Pointer;

public class StructTest {

	private final Memory memory = new Memory.ByteArray(1024);
	private final Pointer pointer = new Pointer(memory, 0);
	private TestStruct struct = new TestStruct(pointer);

	@Before
	public void setUp() {
		struct.byte1().set((byte) 1);
		struct.subStruct2().byte1().set((byte) 2);
		struct.subStruct2().byte2().set((byte) 3);
		struct.byte3().set((byte) 4);
		struct.subStruct4().byte1().set((byte) 5);
		struct.subStruct4().byte2().set((byte) 6);
	}

	@Test
	public void testOffset() {
		assertEquals(0, struct.byte1().offset());
		assertEquals(1, struct.subStruct2().byte1().offset());
		assertEquals(2, struct.subStruct2().byte2().offset());
		assertEquals(3, struct.byte3().offset());
		assertEquals(4, struct.subStruct4().byte1().offset());
		assertEquals(5, struct.subStruct4().byte2().offset());
	}

	@Test
	public void testSet() {
		assertEquals(1, memory.get(0));
		assertEquals(2, memory.get(1));
		assertEquals(3, memory.get(2));
		assertEquals(4, memory.get(3));
		assertEquals(5, memory.get(4));
		assertEquals(6, memory.get(5));
	}

	@Test
	public void testSetGet() {
		assertEquals(1, struct.byte1().get());
		assertEquals(2, struct.subStruct2().byte1().get());
		assertEquals(3, struct.subStruct2().byte2().get());
		assertEquals(4, struct.byte3().get());
		assertEquals(5, struct.subStruct4().byte1().get());
		assertEquals(6, struct.subStruct4().byte2().get());
	}

	@Test
	public void testMove() {
		struct.pointer().move(1);
		assertEquals(2, struct.byte1().get());
		assertEquals(3, struct.subStruct2().byte1().get());
		assertEquals(4, struct.subStruct2().byte2().get());
		assertEquals(5, struct.byte3().get());
		assertEquals(6, struct.subStruct4().byte1().get());
		assertEquals(0, struct.subStruct4().byte2().get());
	}

	static class TestStruct extends Struct {

		public TestStruct(final Pointer pointer) {
			super(pointer);
		}

		public static class TestSubStruct extends Struct {

			private SignedByte byte1 = new SignedByte(this);
			private SignedByte byte2 = new SignedByte(this);

			public TestSubStruct(final TestStruct parent) {
				super(parent);
			}

			public SignedByte byte1() {
				return byte1;
			}

			public SignedByte byte2() {
				return byte2;
			}
		}

		private SignedByte byte1 = new SignedByte(this);

		private TestSubStruct subStruct2 = new TestSubStruct(this);

		private SignedByte byte3 = new SignedByte(this);

		private TestSubStruct subStruct4 = new TestSubStruct(this);

		public SignedByte byte1() {
			return byte1;
		}

		public TestSubStruct subStruct2() {
			return subStruct2;
		}

		public SignedByte byte3() {
			return byte3;
		}

		public TestSubStruct subStruct4() {
			return subStruct4;
		}

	}

}
