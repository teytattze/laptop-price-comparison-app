import { css } from '@emotion/css';
import { Button, Col, Pagination, Row, Skeleton, Typography } from 'antd';
import { Link, useNavigate, useSearchParams } from 'react-router-dom';
import { IProduct } from '../shared/interfaces/products.interface';

type ProductsListingProps = {
  data?: IProduct[];
  isLoading: boolean;
  total: number;
};

export default function ProductsListing({
  data,
  isLoading,
  total,
}: ProductsListingProps) {
  const navigate = useNavigate();
  const [searchParams] = useSearchParams();
  const page = Number(searchParams.get('page')) || 1;

  return (
    <>
      <Skeleton loading={isLoading}>
        <Row gutter={[16, 16]}>
          {data?.length &&
            data?.map((item) => (
              <Col xs={24} sm={8} lg={6} key={item.id}>
                <div className={styles.card}>
                  <img
                    src={item.imageUrl}
                    alt={item.name}
                    className={styles.cardImage}
                  />
                  <div className={styles.cardContent}>
                    <Typography.Title
                      level={5}
                      data-testid={`products-listing-item-title-${item.id}`}
                    >
                      {item.brand} {item.name}
                    </Typography.Title>
                    <Typography.Text
                      type="secondary"
                      data-testid={`products-listing-item-subtitle-${item.id}`}
                    >{`${item.brand} ${item.name} ${item?.screenSize} ${item?.processor} ${item?.graphicsCard}`}</Typography.Text>
                  </div>
                  <Link to={`/products/${item.id}`}>
                    <Button type="primary">More Info</Button>
                  </Link>
                </div>
              </Col>
            ))}
        </Row>
      </Skeleton>
      <div className={styles.paginationWrapper}>
        <Pagination
          total={total}
          defaultCurrent={page}
          defaultPageSize={20}
          showSizeChanger={false}
          onChange={(page, pageSize) =>
            navigate(`?page=${page}&perPage=${pageSize}`)
          }
        />
      </div>
    </>
  );
}

const styles = {
  card: css`
    overflow: hidden;
    height: 100%;
    padding: 1rem 1rem 2rem 1rem;
    border: 1px solid rgb(235, 237, 240);
    display: flex;
    flex-direction: column;
    align-items: center;
  `,
  cardImage: css`
    width: 200px !important;
    margin: 0 auto;
    object-fit: contain;
    object-position: center;
    aspect-ratio: 1/1;
  `,
  cardContent: css`
    flex: 1;
    margin: 1rem 0 1.5rem 0;
  `,
  paginationWrapper: css`
    width: 100%;
    margin-top: 2rem;
    text-align: center;
  `,
};
