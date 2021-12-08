import cors from 'cors';
import express from 'express';
import { productsRouter } from './modules/products/products.router';
import { errorHandling } from './errors/error-handling';

const app = express();

const apiRouter = express.Router();
apiRouter.use('/products', productsRouter);

app.use(cors());
app.use('/api', apiRouter);
app.use(errorHandling);

export { app };
