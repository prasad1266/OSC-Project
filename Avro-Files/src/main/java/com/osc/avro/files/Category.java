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
public class Category extends org.apache.avro.specific.SpecificRecordBase implements org.apache.avro.specific.SpecificRecord {
  private static final long serialVersionUID = 4488629154402684261L;
  public static final org.apache.avro.Schema SCHEMA$ = new org.apache.avro.Schema.Parser().parse("{\"type\":\"record\",\"name\":\"Category\",\"namespace\":\"com.osc.avro.files\",\"fields\":[{\"name\":\"categoryName\",\"type\":\"string\"},{\"name\":\"imagePath\",\"type\":\"string\"}]}");
  public static org.apache.avro.Schema getClassSchema() { return SCHEMA$; }

  private static SpecificData MODEL$ = new SpecificData();

  private static final BinaryMessageEncoder<Category> ENCODER =
      new BinaryMessageEncoder<Category>(MODEL$, SCHEMA$);

  private static final BinaryMessageDecoder<Category> DECODER =
      new BinaryMessageDecoder<Category>(MODEL$, SCHEMA$);

  /**
   * Return the BinaryMessageEncoder instance used by this class.
   * @return the message encoder used by this class
   */
  public static BinaryMessageEncoder<Category> getEncoder() {
    return ENCODER;
  }

  /**
   * Return the BinaryMessageDecoder instance used by this class.
   * @return the message decoder used by this class
   */
  public static BinaryMessageDecoder<Category> getDecoder() {
    return DECODER;
  }

  /**
   * Create a new BinaryMessageDecoder instance for this class that uses the specified {@link SchemaStore}.
   * @param resolver a {@link SchemaStore} used to find schemas by fingerprint
   * @return a BinaryMessageDecoder instance for this class backed by the given SchemaStore
   */
  public static BinaryMessageDecoder<Category> createDecoder(SchemaStore resolver) {
    return new BinaryMessageDecoder<Category>(MODEL$, SCHEMA$, resolver);
  }

  /**
   * Serializes this Category to a ByteBuffer.
   * @return a buffer holding the serialized data for this instance
   * @throws java.io.IOException if this instance could not be serialized
   */
  public java.nio.ByteBuffer toByteBuffer() throws java.io.IOException {
    return ENCODER.encode(this);
  }

  /**
   * Deserializes a Category from a ByteBuffer.
   * @param b a byte buffer holding serialized data for an instance of this class
   * @return a Category instance decoded from the given buffer
   * @throws java.io.IOException if the given bytes could not be deserialized into an instance of this class
   */
  public static Category fromByteBuffer(
      java.nio.ByteBuffer b) throws java.io.IOException {
    return DECODER.decode(b);
  }

   private java.lang.CharSequence categoryName;
   private java.lang.CharSequence imagePath;

  /**
   * Default constructor.  Note that this does not initialize fields
   * to their default values from the schema.  If that is desired then
   * one should use <code>newBuilder()</code>.
   */
  public Category() {}

  /**
   * All-args constructor.
   * @param categoryName The new value for categoryName
   * @param imagePath The new value for imagePath
   */
  public Category(java.lang.CharSequence categoryName, java.lang.CharSequence imagePath) {
    this.categoryName = categoryName;
    this.imagePath = imagePath;
  }

  public org.apache.avro.specific.SpecificData getSpecificData() { return MODEL$; }
  public org.apache.avro.Schema getSchema() { return SCHEMA$; }
  // Used by DatumWriter.  Applications should not call.
  public java.lang.Object get(int field$) {
    switch (field$) {
    case 0: return categoryName;
    case 1: return imagePath;
    default: throw new IndexOutOfBoundsException("Invalid index: " + field$);
    }
  }

  // Used by DatumReader.  Applications should not call.
  @SuppressWarnings(value="unchecked")
  public void put(int field$, java.lang.Object value$) {
    switch (field$) {
    case 0: categoryName = (java.lang.CharSequence)value$; break;
    case 1: imagePath = (java.lang.CharSequence)value$; break;
    default: throw new IndexOutOfBoundsException("Invalid index: " + field$);
    }
  }

  /**
   * Gets the value of the 'categoryName' field.
   * @return The value of the 'categoryName' field.
   */
  public java.lang.CharSequence getCategoryName() {
    return categoryName;
  }


  /**
   * Sets the value of the 'categoryName' field.
   * @param value the value to set.
   */
  public void setCategoryName(java.lang.CharSequence value) {
    this.categoryName = value;
  }

  /**
   * Gets the value of the 'imagePath' field.
   * @return The value of the 'imagePath' field.
   */
  public java.lang.CharSequence getImagePath() {
    return imagePath;
  }


  /**
   * Sets the value of the 'imagePath' field.
   * @param value the value to set.
   */
  public void setImagePath(java.lang.CharSequence value) {
    this.imagePath = value;
  }

  /**
   * Creates a new Category RecordBuilder.
   * @return A new Category RecordBuilder
   */
  public static com.osc.avro.files.Category.Builder newBuilder() {
    return new com.osc.avro.files.Category.Builder();
  }

  /**
   * Creates a new Category RecordBuilder by copying an existing Builder.
   * @param other The existing builder to copy.
   * @return A new Category RecordBuilder
   */
  public static com.osc.avro.files.Category.Builder newBuilder(com.osc.avro.files.Category.Builder other) {
    if (other == null) {
      return new com.osc.avro.files.Category.Builder();
    } else {
      return new com.osc.avro.files.Category.Builder(other);
    }
  }

  /**
   * Creates a new Category RecordBuilder by copying an existing Category instance.
   * @param other The existing instance to copy.
   * @return A new Category RecordBuilder
   */
  public static com.osc.avro.files.Category.Builder newBuilder(com.osc.avro.files.Category other) {
    if (other == null) {
      return new com.osc.avro.files.Category.Builder();
    } else {
      return new com.osc.avro.files.Category.Builder(other);
    }
  }

  /**
   * RecordBuilder for Category instances.
   */
  @org.apache.avro.specific.AvroGenerated
  public static class Builder extends org.apache.avro.specific.SpecificRecordBuilderBase<Category>
    implements org.apache.avro.data.RecordBuilder<Category> {

    private java.lang.CharSequence categoryName;
    private java.lang.CharSequence imagePath;

    /** Creates a new Builder */
    private Builder() {
      super(SCHEMA$);
    }

    /**
     * Creates a Builder by copying an existing Builder.
     * @param other The existing Builder to copy.
     */
    private Builder(com.osc.avro.files.Category.Builder other) {
      super(other);
      if (isValidValue(fields()[0], other.categoryName)) {
        this.categoryName = data().deepCopy(fields()[0].schema(), other.categoryName);
        fieldSetFlags()[0] = other.fieldSetFlags()[0];
      }
      if (isValidValue(fields()[1], other.imagePath)) {
        this.imagePath = data().deepCopy(fields()[1].schema(), other.imagePath);
        fieldSetFlags()[1] = other.fieldSetFlags()[1];
      }
    }

    /**
     * Creates a Builder by copying an existing Category instance
     * @param other The existing instance to copy.
     */
    private Builder(com.osc.avro.files.Category other) {
      super(SCHEMA$);
      if (isValidValue(fields()[0], other.categoryName)) {
        this.categoryName = data().deepCopy(fields()[0].schema(), other.categoryName);
        fieldSetFlags()[0] = true;
      }
      if (isValidValue(fields()[1], other.imagePath)) {
        this.imagePath = data().deepCopy(fields()[1].schema(), other.imagePath);
        fieldSetFlags()[1] = true;
      }
    }

    /**
      * Gets the value of the 'categoryName' field.
      * @return The value.
      */
    public java.lang.CharSequence getCategoryName() {
      return categoryName;
    }


    /**
      * Sets the value of the 'categoryName' field.
      * @param value The value of 'categoryName'.
      * @return This builder.
      */
    public com.osc.avro.files.Category.Builder setCategoryName(java.lang.CharSequence value) {
      validate(fields()[0], value);
      this.categoryName = value;
      fieldSetFlags()[0] = true;
      return this;
    }

    /**
      * Checks whether the 'categoryName' field has been set.
      * @return True if the 'categoryName' field has been set, false otherwise.
      */
    public boolean hasCategoryName() {
      return fieldSetFlags()[0];
    }


    /**
      * Clears the value of the 'categoryName' field.
      * @return This builder.
      */
    public com.osc.avro.files.Category.Builder clearCategoryName() {
      categoryName = null;
      fieldSetFlags()[0] = false;
      return this;
    }

    /**
      * Gets the value of the 'imagePath' field.
      * @return The value.
      */
    public java.lang.CharSequence getImagePath() {
      return imagePath;
    }


    /**
      * Sets the value of the 'imagePath' field.
      * @param value The value of 'imagePath'.
      * @return This builder.
      */
    public com.osc.avro.files.Category.Builder setImagePath(java.lang.CharSequence value) {
      validate(fields()[1], value);
      this.imagePath = value;
      fieldSetFlags()[1] = true;
      return this;
    }

    /**
      * Checks whether the 'imagePath' field has been set.
      * @return True if the 'imagePath' field has been set, false otherwise.
      */
    public boolean hasImagePath() {
      return fieldSetFlags()[1];
    }


    /**
      * Clears the value of the 'imagePath' field.
      * @return This builder.
      */
    public com.osc.avro.files.Category.Builder clearImagePath() {
      imagePath = null;
      fieldSetFlags()[1] = false;
      return this;
    }

    @Override
    @SuppressWarnings("unchecked")
    public Category build() {
      try {
        Category record = new Category();
        record.categoryName = fieldSetFlags()[0] ? this.categoryName : (java.lang.CharSequence) defaultValue(fields()[0]);
        record.imagePath = fieldSetFlags()[1] ? this.imagePath : (java.lang.CharSequence) defaultValue(fields()[1]);
        return record;
      } catch (org.apache.avro.AvroMissingFieldException e) {
        throw e;
      } catch (java.lang.Exception e) {
        throw new org.apache.avro.AvroRuntimeException(e);
      }
    }
  }

  @SuppressWarnings("unchecked")
  private static final org.apache.avro.io.DatumWriter<Category>
    WRITER$ = (org.apache.avro.io.DatumWriter<Category>)MODEL$.createDatumWriter(SCHEMA$);

  @Override public void writeExternal(java.io.ObjectOutput out)
    throws java.io.IOException {
    WRITER$.write(this, SpecificData.getEncoder(out));
  }

  @SuppressWarnings("unchecked")
  private static final org.apache.avro.io.DatumReader<Category>
    READER$ = (org.apache.avro.io.DatumReader<Category>)MODEL$.createDatumReader(SCHEMA$);

  @Override public void readExternal(java.io.ObjectInput in)
    throws java.io.IOException {
    READER$.read(this, SpecificData.getDecoder(in));
  }

  @Override protected boolean hasCustomCoders() { return true; }

  @Override public void customEncode(org.apache.avro.io.Encoder out)
    throws java.io.IOException
  {
    out.writeString(this.categoryName);

    out.writeString(this.imagePath);

  }

  @Override public void customDecode(org.apache.avro.io.ResolvingDecoder in)
    throws java.io.IOException
  {
    org.apache.avro.Schema.Field[] fieldOrder = in.readFieldOrderIfDiff();
    if (fieldOrder == null) {
      this.categoryName = in.readString(this.categoryName instanceof Utf8 ? (Utf8)this.categoryName : null);

      this.imagePath = in.readString(this.imagePath instanceof Utf8 ? (Utf8)this.imagePath : null);

    } else {
      for (int i = 0; i < 2; i++) {
        switch (fieldOrder[i].pos()) {
        case 0:
          this.categoryName = in.readString(this.categoryName instanceof Utf8 ? (Utf8)this.categoryName : null);
          break;

        case 1:
          this.imagePath = in.readString(this.imagePath instanceof Utf8 ? (Utf8)this.imagePath : null);
          break;

        default:
          throw new java.io.IOException("Corrupt ResolvingDecoder.");
        }
      }
    }
  }
}









