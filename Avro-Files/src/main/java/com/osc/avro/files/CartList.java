/**
 * Autogenerated by Avro
 *
 * DO NOT EDIT DIRECTLY
 */
package com.osc.avro.files;

import org.apache.avro.generic.GenericArray;
import org.apache.avro.specific.SpecificData;
import org.apache.avro.util.Utf8;
import org.apache.avro.message.BinaryMessageEncoder;
import org.apache.avro.message.BinaryMessageDecoder;
import org.apache.avro.message.SchemaStore;

@org.apache.avro.specific.AvroGenerated
public class CartList extends org.apache.avro.specific.SpecificRecordBase implements org.apache.avro.specific.SpecificRecord {
  private static final long serialVersionUID = 5277849378957867633L;
  public static final org.apache.avro.Schema SCHEMA$ = new org.apache.avro.Schema.Parser().parse("{\"type\":\"record\",\"name\":\"CartList\",\"namespace\":\"com.osc.avro.files\",\"fields\":[{\"name\":\"products\",\"type\":{\"type\":\"array\",\"items\":{\"type\":\"record\",\"name\":\"CartProduct\",\"fields\":[{\"name\":\"productId\",\"type\":\"string\"},{\"name\":\"productQuantity\",\"type\":\"int\"}]}}}]}");
  public static org.apache.avro.Schema getClassSchema() { return SCHEMA$; }

  private static SpecificData MODEL$ = new SpecificData();

  private static final BinaryMessageEncoder<CartList> ENCODER =
      new BinaryMessageEncoder<CartList>(MODEL$, SCHEMA$);

  private static final BinaryMessageDecoder<CartList> DECODER =
      new BinaryMessageDecoder<CartList>(MODEL$, SCHEMA$);

  /**
   * Return the BinaryMessageEncoder instance used by this class.
   * @return the message encoder used by this class
   */
  public static BinaryMessageEncoder<CartList> getEncoder() {
    return ENCODER;
  }

  /**
   * Return the BinaryMessageDecoder instance used by this class.
   * @return the message decoder used by this class
   */
  public static BinaryMessageDecoder<CartList> getDecoder() {
    return DECODER;
  }

  /**
   * Create a new BinaryMessageDecoder instance for this class that uses the specified {@link SchemaStore}.
   * @param resolver a {@link SchemaStore} used to find schemas by fingerprint
   * @return a BinaryMessageDecoder instance for this class backed by the given SchemaStore
   */
  public static BinaryMessageDecoder<CartList> createDecoder(SchemaStore resolver) {
    return new BinaryMessageDecoder<CartList>(MODEL$, SCHEMA$, resolver);
  }

  /**
   * Serializes this CartList to a ByteBuffer.
   * @return a buffer holding the serialized data for this instance
   * @throws java.io.IOException if this instance could not be serialized
   */
  public java.nio.ByteBuffer toByteBuffer() throws java.io.IOException {
    return ENCODER.encode(this);
  }

  /**
   * Deserializes a CartList from a ByteBuffer.
   * @param b a byte buffer holding serialized data for an instance of this class
   * @return a CartList instance decoded from the given buffer
   * @throws java.io.IOException if the given bytes could not be deserialized into an instance of this class
   */
  public static CartList fromByteBuffer(
      java.nio.ByteBuffer b) throws java.io.IOException {
    return DECODER.decode(b);
  }

   private java.util.List<com.osc.avro.files.CartProduct> products;

  /**
   * Default constructor.  Note that this does not initialize fields
   * to their default values from the schema.  If that is desired then
   * one should use <code>newBuilder()</code>.
   */
  public CartList() {}

  /**
   * All-args constructor.
   * @param products The new value for products
   */
  public CartList(java.util.List<com.osc.avro.files.CartProduct> products) {
    this.products = products;
  }

  public org.apache.avro.specific.SpecificData getSpecificData() { return MODEL$; }
  public org.apache.avro.Schema getSchema() { return SCHEMA$; }
  // Used by DatumWriter.  Applications should not call.
  public java.lang.Object get(int field$) {
    switch (field$) {
    case 0: return products;
    default: throw new IndexOutOfBoundsException("Invalid index: " + field$);
    }
  }

  // Used by DatumReader.  Applications should not call.
  @SuppressWarnings(value="unchecked")
  public void put(int field$, java.lang.Object value$) {
    switch (field$) {
    case 0: products = (java.util.List<com.osc.avro.files.CartProduct>)value$; break;
    default: throw new IndexOutOfBoundsException("Invalid index: " + field$);
    }
  }

  /**
   * Gets the value of the 'products' field.
   * @return The value of the 'products' field.
   */
  public java.util.List<com.osc.avro.files.CartProduct> getProducts() {
    return products;
  }


  /**
   * Sets the value of the 'products' field.
   * @param value the value to set.
   */
  public void setProducts(java.util.List<com.osc.avro.files.CartProduct> value) {
    this.products = value;
  }

  /**
   * Creates a new CartList RecordBuilder.
   * @return A new CartList RecordBuilder
   */
  public static com.osc.avro.files.CartList.Builder newBuilder() {
    return new com.osc.avro.files.CartList.Builder();
  }

  /**
   * Creates a new CartList RecordBuilder by copying an existing Builder.
   * @param other The existing builder to copy.
   * @return A new CartList RecordBuilder
   */
  public static com.osc.avro.files.CartList.Builder newBuilder(com.osc.avro.files.CartList.Builder other) {
    if (other == null) {
      return new com.osc.avro.files.CartList.Builder();
    } else {
      return new com.osc.avro.files.CartList.Builder(other);
    }
  }

  /**
   * Creates a new CartList RecordBuilder by copying an existing CartList instance.
   * @param other The existing instance to copy.
   * @return A new CartList RecordBuilder
   */
  public static com.osc.avro.files.CartList.Builder newBuilder(com.osc.avro.files.CartList other) {
    if (other == null) {
      return new com.osc.avro.files.CartList.Builder();
    } else {
      return new com.osc.avro.files.CartList.Builder(other);
    }
  }

  /**
   * RecordBuilder for CartList instances.
   */
  @org.apache.avro.specific.AvroGenerated
  public static class Builder extends org.apache.avro.specific.SpecificRecordBuilderBase<CartList>
    implements org.apache.avro.data.RecordBuilder<CartList> {

    private java.util.List<com.osc.avro.files.CartProduct> products;

    /** Creates a new Builder */
    private Builder() {
      super(SCHEMA$);
    }

    /**
     * Creates a Builder by copying an existing Builder.
     * @param other The existing Builder to copy.
     */
    private Builder(com.osc.avro.files.CartList.Builder other) {
      super(other);
      if (isValidValue(fields()[0], other.products)) {
        this.products = data().deepCopy(fields()[0].schema(), other.products);
        fieldSetFlags()[0] = other.fieldSetFlags()[0];
      }
    }

    /**
     * Creates a Builder by copying an existing CartList instance
     * @param other The existing instance to copy.
     */
    private Builder(com.osc.avro.files.CartList other) {
      super(SCHEMA$);
      if (isValidValue(fields()[0], other.products)) {
        this.products = data().deepCopy(fields()[0].schema(), other.products);
        fieldSetFlags()[0] = true;
      }
    }

    /**
      * Gets the value of the 'products' field.
      * @return The value.
      */
    public java.util.List<com.osc.avro.files.CartProduct> getProducts() {
      return products;
    }


    /**
      * Sets the value of the 'products' field.
      * @param value The value of 'products'.
      * @return This builder.
      */
    public com.osc.avro.files.CartList.Builder setProducts(java.util.List<com.osc.avro.files.CartProduct> value) {
      validate(fields()[0], value);
      this.products = value;
      fieldSetFlags()[0] = true;
      return this;
    }

    /**
      * Checks whether the 'products' field has been set.
      * @return True if the 'products' field has been set, false otherwise.
      */
    public boolean hasProducts() {
      return fieldSetFlags()[0];
    }


    /**
      * Clears the value of the 'products' field.
      * @return This builder.
      */
    public com.osc.avro.files.CartList.Builder clearProducts() {
      products = null;
      fieldSetFlags()[0] = false;
      return this;
    }

    @Override
    @SuppressWarnings("unchecked")
    public CartList build() {
      try {
        CartList record = new CartList();
        record.products = fieldSetFlags()[0] ? this.products : (java.util.List<com.osc.avro.files.CartProduct>) defaultValue(fields()[0]);
        return record;
      } catch (org.apache.avro.AvroMissingFieldException e) {
        throw e;
      } catch (java.lang.Exception e) {
        throw new org.apache.avro.AvroRuntimeException(e);
      }
    }
  }

  @SuppressWarnings("unchecked")
  private static final org.apache.avro.io.DatumWriter<CartList>
    WRITER$ = (org.apache.avro.io.DatumWriter<CartList>)MODEL$.createDatumWriter(SCHEMA$);

  @Override public void writeExternal(java.io.ObjectOutput out)
    throws java.io.IOException {
    WRITER$.write(this, SpecificData.getEncoder(out));
  }

  @SuppressWarnings("unchecked")
  private static final org.apache.avro.io.DatumReader<CartList>
    READER$ = (org.apache.avro.io.DatumReader<CartList>)MODEL$.createDatumReader(SCHEMA$);

  @Override public void readExternal(java.io.ObjectInput in)
    throws java.io.IOException {
    READER$.read(this, SpecificData.getDecoder(in));
  }

  @Override protected boolean hasCustomCoders() { return true; }

  @Override public void customEncode(org.apache.avro.io.Encoder out)
    throws java.io.IOException
  {
    long size0 = this.products.size();
    out.writeArrayStart();
    out.setItemCount(size0);
    long actualSize0 = 0;
    for (com.osc.avro.files.CartProduct e0: this.products) {
      actualSize0++;
      out.startItem();
      e0.customEncode(out);
    }
    out.writeArrayEnd();
    if (actualSize0 != size0)
      throw new java.util.ConcurrentModificationException("Array-size written was " + size0 + ", but element count was " + actualSize0 + ".");

  }

  @Override public void customDecode(org.apache.avro.io.ResolvingDecoder in)
    throws java.io.IOException
  {
    org.apache.avro.Schema.Field[] fieldOrder = in.readFieldOrderIfDiff();
    if (fieldOrder == null) {
      long size0 = in.readArrayStart();
      java.util.List<com.osc.avro.files.CartProduct> a0 = this.products;
      if (a0 == null) {
        a0 = new SpecificData.Array<com.osc.avro.files.CartProduct>((int)size0, SCHEMA$.getField("products").schema());
        this.products = a0;
      } else a0.clear();
      SpecificData.Array<com.osc.avro.files.CartProduct> ga0 = (a0 instanceof SpecificData.Array ? (SpecificData.Array<com.osc.avro.files.CartProduct>)a0 : null);
      for ( ; 0 < size0; size0 = in.arrayNext()) {
        for ( ; size0 != 0; size0--) {
          com.osc.avro.files.CartProduct e0 = (ga0 != null ? ga0.peek() : null);
          if (e0 == null) {
            e0 = new com.osc.avro.files.CartProduct();
          }
          e0.customDecode(in);
          a0.add(e0);
        }
      }

    } else {
      for (int i = 0; i < 1; i++) {
        switch (fieldOrder[i].pos()) {
        case 0:
          long size0 = in.readArrayStart();
          java.util.List<com.osc.avro.files.CartProduct> a0 = this.products;
          if (a0 == null) {
            a0 = new SpecificData.Array<com.osc.avro.files.CartProduct>((int)size0, SCHEMA$.getField("products").schema());
            this.products = a0;
          } else a0.clear();
          SpecificData.Array<com.osc.avro.files.CartProduct> ga0 = (a0 instanceof SpecificData.Array ? (SpecificData.Array<com.osc.avro.files.CartProduct>)a0 : null);
          for ( ; 0 < size0; size0 = in.arrayNext()) {
            for ( ; size0 != 0; size0--) {
              com.osc.avro.files.CartProduct e0 = (ga0 != null ? ga0.peek() : null);
              if (e0 == null) {
                e0 = new com.osc.avro.files.CartProduct();
              }
              e0.customDecode(in);
              a0.add(e0);
            }
          }
          break;

        default:
          throw new java.io.IOException("Corrupt ResolvingDecoder.");
        }
      }
    }
  }
}









