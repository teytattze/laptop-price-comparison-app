import { Request } from 'express';
import { IPagination } from '../shared/interfaces/pagination.interface';

export const paginationHelper = (req: Request): IPagination => {
  const page: string = (req.query['page'] as string) || '1';
  const perPage: string = (req.query['perPage'] as string) || '20';
  const offset: number = (Number(page) - 1) * Number(perPage);
  return { page, perPage, offset };
};
