package com.j256.simplemagic;

import java.io.Serializable;

/**
 * Information associated with some content, returned by the magic matching code in
 * {@link ContentInfoUtil#findMatch(String)} and other methods.
 * 
 * @author graywatson
 */
public class ContentInfo implements Serializable {

	private static final long serialVersionUID = 1342819252130963539L;

	private final ContentType contentType;
	private final String name;
	private final String message;
	private final String mimeType;
	private final String[] fileExtensions;
	private final boolean partial;

	public ContentInfo(String name, String mimeType, String message, boolean partial) {
		this.contentType = ContentType.fromMimeType(mimeType);
		if (this.contentType == ContentType.OTHER) {
			this.name = name;
			this.fileExtensions = null;
		} else {
			this.name = this.contentType.getSimpleName();
			this.fileExtensions = this.contentType.getFileExtensions();
		}
		this.mimeType = mimeType;
		this.message = message;
		this.partial = partial;
	}

	public ContentInfo(ContentType contentType) {
		this.contentType = contentType;
		this.name = contentType.getSimpleName();
		this.mimeType = contentType.getMimeType();
		this.message = null;
		this.fileExtensions = contentType.getFileExtensions();
		this.partial = false;
	}

	/**
	 * Returns the internal enumerated type associated with the content or {@link ContentType#OTHER} if not known.
	 */
	public ContentType getContentType() {
		return contentType;
	}

	/**
	 * Returns the short name of the content either from the content-type or extracted from the message. If the
	 * content-type is known then this is a specific name string. Otherwise this is usually the first word of the
	 * message generated by the magic file.
	 */
	public String getName() {
		return name;
	}

	/**
	 * Returns the mime-type or null if none.
	 */
	public String getMimeType() {
		return mimeType;
	}

	/**
	 * Returns the full message as generated by the magic matching code or null if none. This should be similar to the
	 * output from the Unix file(1) command.
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * Returns an array of associated file-extensions or null if none.
	 */
	public String[] getFileExtensions() {
		return fileExtensions;
	}

	/**
	 * Whether or not this was a partial match. For some of the types, there is a main matching pattern and then more
	 * specific patterns which detect additional features of the type. A partial match means that none of the more
	 * specific patterns fully matched the content. It's probably still of the type but just not a variant that the
	 * library knows about.
	 */
	public boolean isPartial() {
		return partial;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(name);
		if (contentType != null) {
			sb.append(", type ").append(contentType);
		}
		if (mimeType != null) {
			sb.append(", mime '").append(mimeType).append('\'');
		}
		if (message != null) {
			sb.append(", msg '").append(message).append('\'');
		}
		return sb.toString();
	}
}
