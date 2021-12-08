export class HttpException extends Error {
  constructor(private statusCode: number, message: string) {
    super();
    this.message = message;
  }

  public getStatusCode(): number {
    return this.statusCode;
  }
}

export class BadRequestException extends HttpException {
  constructor(message: string) {
    super(400, message);
  }
}

export class NotFoundException extends HttpException {
  constructor(message: string) {
    super(404, message);
  }
}
