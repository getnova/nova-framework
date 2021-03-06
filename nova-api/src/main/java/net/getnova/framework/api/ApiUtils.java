package net.getnova.framework.api;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.exc.InvalidDefinitionException;
import com.fasterxml.jackson.databind.exc.MismatchedInputException;
import io.netty.handler.codec.http.HttpResponseStatus;
import net.getnova.framework.api.data.response.ApiError;

/**
 * Utility methods for the api system.
 */
public final class ApiUtils {

  private ApiUtils() {
    throw new UnsupportedOperationException();
  }

  /**
   * Maps a json exception into a {@link ApiException}.
   *
   * @param cause the {@link Throwable} witch possibly should be mapped.
   * @return a possibly mapped {@link Throwable}.
   * @see ApiException
   */
  public static Throwable mapJsonError(final Throwable cause) {
    if (cause instanceof InvalidDefinitionException) {
      return cause;
    }

    if (cause instanceof JsonParseException) {
      return ApiException.of(HttpResponseStatus.BAD_REQUEST, ApiError.of("JSON", "SYNTAX"));
    }

    if (cause instanceof MismatchedInputException) {
      return ApiException.of(HttpResponseStatus.BAD_REQUEST, ApiError.of("JSON", "UNEXPECTED_CONTENT"));
    }

    return cause;
  }
}
