/*
 * Copyright (c) 2012, FDV Solutions S.A.
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *     * Redistributions of source code must retain the above copyright
 *       notice, this list of conditions and the following disclaimer.
 *     * Redistributions in binary form must reproduce the above copyright
 *       notice, this list of conditions and the following disclaimer in the
 *       documentation and/or other materials provided with the distribution.
 *     * Neither the name of the FDV Solutions S.A. nor the
 *       names of its contributors may be used to endorse or promote products
 *       derived from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL FDV Solutions S.A. BE LIABLE FOR ANY
 * DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package ar.com.fdvs.dj.util;

	import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

	/**
 * Provides utility methods for input and output streams.
 *
 * @author Richard Wan
 * copied from log4j StreamUtils class
 */

// Contributed by ThoughtWorks Inc.

public abstract class StreamUtils {
  //--------------------------------------------------------------------------
  //   Constants:
  //--------------------------------------------------------------------------

  /**
   * Default value is 2048.
   */
  public static final int DEFAULT_BUFFER_SIZE = 2048;


  /**
   * Copies information from the input stream to the output stream using
   * a default buffer size of 2048 bytes.
   * @throws java.io.IOException
   */
  public static void copy(InputStream input, OutputStream output)
      throws IOException {
    copy(input, output, DEFAULT_BUFFER_SIZE);
  }

  /**
   * Copies information from the input stream to the output stream using
   * the specified buffer size
   * @throws java.io.IOException
   */
  public static void copy(InputStream input,
      OutputStream output,
      int bufferSize)
      throws IOException {
    byte[] buf = new byte[bufferSize];
    int bytesRead = input.read(buf);
    while (bytesRead != -1) {
      output.write(buf, 0, bytesRead);
      bytesRead = input.read(buf);
    }
    output.flush();
  }

  /**
   * Copies information between specified streams and then closes
   * both of the streams.
   * @throws java.io.IOException
   */
  public static void copyThenClose(InputStream input, OutputStream output)
      throws IOException {
    copy(input, output);
    input.close();
    output.close();
  }

  /**
   * @returns a byte[] containing the information contained in the
   * specified InputStream.
   * @throws java.io.IOException
   */
	  public static byte[] getBytes(InputStream input)
	      throws IOException {
	    ByteArrayOutputStream result = new ByteArrayOutputStream();
	    copy(input, result);
	    result.close();
	    return result.toByteArray();
	  }

}
