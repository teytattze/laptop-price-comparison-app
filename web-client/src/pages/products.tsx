import { useQuery } from 'react-query';
import { useSearchParams } from 'react-router-dom';
import {
  getProductsCount,
  listProducts,
} from '../services/api-products.service';
import { IProduct } from '../shared/interfaces/products.interface';
import {
  GET_PRODUCTS_COUNT_KEY,
  LIST_PRODUCTS_KEY,
} from '../shared/constants/products.const';
import ProductsListing from '../components/products-listing';
import { Alert } from 'antd';

export default function Products() {
  const [searchParams] = useSearchParams();
  const page = Number(searchParams.get('page')) || 1;
  const perPage = Number(searchParams.get('perPage')) || 20;

  const { data: total } = useQuery(GET_PRODUCTS_COUNT_KEY, getProductsCount);
  const { data, isLoading } = useQuery<IProduct[]>(
    [LIST_PRODUCTS_KEY, page, perPage],
    () => listProducts({ page, perPage }),
    { enabled: Boolean(total) },
  );

  return (
    <>
      {!data && !isLoading ? (
        <Alert
          message="Error"
          description="There is no data available currently..."
          type="error"
          showIcon
        />
      ) : (
        <ProductsListing data={data} isLoading={isLoading} total={total} />
      )}
    </>
  );
}
