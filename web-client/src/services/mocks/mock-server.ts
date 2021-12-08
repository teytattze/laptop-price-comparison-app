import { setupServer } from 'msw/node';
import { handlers as apiProductsHandlers } from './api-products.service.mock';

export const server = setupServer(...apiProductsHandlers);
