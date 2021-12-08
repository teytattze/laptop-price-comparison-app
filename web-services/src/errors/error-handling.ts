import { NextFunction, Request, Response } from 'express';
import { HttpException } from './http-exceptions';

export const errorHandling = (
  err: any | HttpException,
  req: Request,
  res: Response,
  next: NextFunction,
) => {
  if (err instanceof HttpException) {
    res.status(err.getStatusCode()).json({
      statusCode: err.getStatusCode(),
      message: err.message,
    });
  }
  res.status(500).json({
    statusCode: 500,
    message: 'Internal server error',
  });
};
