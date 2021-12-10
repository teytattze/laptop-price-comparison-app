import { useQuery } from 'react-query';
import { useParams, useSearchParams } from 'react-router-dom';
import {
  getProductsBrandCount,
  listProductsByBrand,
} from '../services/api-products.service';
import { IProduct } from '../shared/interfaces/products.interface';
import {
  GET_PRODUCTS_BY_BRAND_COUNT_KEY,
  LIST_PRODUCTS_KEY,
} from '../shared/constants/products.const';
import ProductsListing from '../components/products-listing';
import { Alert } from 'antd';

export default function ProductsByBrand() {
  const { brand } = useParams();
  const [searchParams] = useSearchParams();
  const page = Number(searchParams.get('page')) || 1;
  const perPage = Number(searchParams.get('perPage')) || 20;

  const { data: total } = useQuery(
    [GET_PRODUCTS_BY_BRAND_COUNT_KEY, brand],
    () => getProductsBrandCount(brand as string),
  );
  const { data, isLoading, isError } = useQuery<IProduct[]>(
    [LIST_PRODUCTS_KEY, page, perPage, brand],
    () => listProductsByBrand(brand as string, { page, perPage }),
    { enabled: Boolean(total) },
  );

  return (
    <>
      {isError ? (
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
