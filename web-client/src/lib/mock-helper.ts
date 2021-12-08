import { PathParams, RestRequest } from 'msw';
import { IPagination } from '../shared/interfaces/pagination.interface';

export const getPagination = (
  req: RestRequest<never, PathParams>,
): IPagination => {
  const page: number = Number(req.url.searchParams.get('page')) || 1;
  const perPage: number = Number(req.url.searchParams.get('perPage')) || 20;
  const offset: number = (Number(page) - 1) * Number(perPage);
  return { page, perPage, offset };
};
