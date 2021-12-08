import { css } from '@emotion/css';
import { Select, Typography } from 'antd';
import { useQuery } from 'react-query';
import { LIST_PRODUCTS_BRANDS_KEY } from '../shared/constants/products.const';
import { listProductsBrands } from '../services/api-products.service';
import { IBrand } from '../shared/interfaces/products.interface';
import { useNavigate } from 'react-router-dom';

export default function Home() {
  const navigate = useNavigate();
  const { data: brands } = useQuery<IBrand[]>(
    LIST_PRODUCTS_BRANDS_KEY,
    listProductsBrands,
  );

  return (
    <div className={styles.base}>
      <Typography.Title
        className={styles.title}
        level={1}
        data-testid="home-header"
      >
        Gaming Laptop's Brands
      </Typography.Title>
      <form className={styles.form} data-testid="home-form">
        <Select
          className={styles.input}
          size="large"
          showSearch
          onSelect={(value) => {
            value === 'ALL'
              ? navigate(`products`)
              : navigate(`products/brands/${value}`);
          }}
        >
          <Select.Option value="ALL">All</Select.Option>
          {brands &&
            brands?.map((brand) => (
              <Select.Option
                className={styles.inputOption}
                key={brand.id}
                value={brand.name.toLowerCase()}
              >
                {brand.name.toLowerCase()}
              </Select.Option>
            ))}
        </Select>
      </form>
    </div>
  );
}

const styles = {
  base: css`
    display: flex;
    flex-direction: column;
    align-items: center;
    justify-content: center;
    height: 90vh;
    width: 100%;
  `,
  title: css`
    margin-bottom: 2rem !important;
  `,
  form: css`
    display: flex;
    justify-content: center;
    width: 100%;
  `,
  input: css`
    width: 100%;
    max-width: 600px;
  `,
  inputOption: css`
    text-transform: capitalize;
  `,
};
