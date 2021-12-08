import { css } from '@emotion/css';
import {
  Alert,
  Col,
  Descriptions,
  Divider,
  List,
  PageHeader,
  Row,
  Skeleton,
  Space,
  Typography,
} from 'antd';
import { useQuery } from 'react-query';
import { useNavigate, useParams } from 'react-router';
import { getProduct } from '../services/api-products.service';
import { IProduct } from '../shared/interfaces/products.interface';
import { GET_PRODUCT_KEY } from '../shared/constants/products.const';

export default function Product() {
  const navigate = useNavigate();
  const params = useParams();
  const id = params.id as string;

  const { data, isLoading } = useQuery<IProduct>([GET_PRODUCT_KEY, id], () =>
    getProduct(id),
  );

  return (
    <>
      <PageHeader
        style={{ paddingLeft: '0', paddingRight: '0' }}
        title="Product Details"
        onBack={() => navigate(-1)}
      />
      <Skeleton loading={isLoading}>
        {!Boolean(data) ? (
          <Alert
            message="Error"
            description="There is an error occurs...The item is not available!"
            type="error"
            showIcon
          />
        ) : (
          <Space direction="vertical" size={32}>
            <Row gutter={[0, 16]}>
              <Col
                xs={24}
                sm={24}
                md={10}
                lg={8}
                className={styles.productImageWrapper}
              >
                <img
                  src={data?.imageUrl}
                  alt={data?.name}
                  className={styles.productImage}
                />
              </Col>
              <Col
                xs={24}
                sm={24}
                md={14}
                lg={16}
                className={styles.productDetails}
              >
                <Typography.Title level={2} data-testid="product-title">
                  {data?.brand} {data?.name} {data?.screenSize}
                </Typography.Title>
                <Typography.Title
                  level={4}
                  type="secondary"
                  data-testid="product-subtitle"
                >
                  {data?.processor}, {data?.graphicsCard}, {data?.ram} RAM
                  {', '}
                  {data?.ssd} SSD {data?.hdd && `+ ${data.hdd}`}
                </Typography.Title>
                <Divider />
                <div>
                  <Typography.Title level={4}>Overview</Typography.Title>
                  <Descriptions
                    size="small"
                    column={1}
                    labelStyle={{
                      fontWeight: '500',
                      color: 'rgba(0, 0, 0, 0.45)',
                    }}
                  >
                    <Descriptions.Item label="Processor">
                      {data?.processor}
                    </Descriptions.Item>
                    <Descriptions.Item label="Graphic">
                      {data?.graphicsCard}
                    </Descriptions.Item>
                    <Descriptions.Item label="Ram">
                      {data?.ram}
                    </Descriptions.Item>
                    <Descriptions.Item label="Storage">
                      {data?.hdd
                        ? `${data?.ssd} SSD + ${data?.hdd} HDD`
                        : `${data?.ssd} SSD`}
                    </Descriptions.Item>
                    <Descriptions.Item label="Screen Size">
                      {data?.screenSize}
                    </Descriptions.Item>
                  </Descriptions>
                </div>
              </Col>
            </Row>
            <div>
              <Typography.Title level={2}>Source</Typography.Title>
              <List
                bordered
                dataSource={data?.sources}
                renderItem={(item) => (
                  <List.Item className={styles.productSourceItem}>
                    <a
                      target="_blank"
                      href={item.url}
                      className={styles.productSourceLink}
                      data-testid={`product-source-link-${item.website}`}
                    >
                      <Typography.Title level={5} style={{ margin: 0 }}>
                        {item.website}
                      </Typography.Title>
                      <Typography.Paragraph
                        className={styles.productSourcePrice}
                        type="secondary"
                      >
                        Price &#163;{item.price}
                      </Typography.Paragraph>
                    </a>
                  </List.Item>
                )}
              />
            </div>
          </Space>
        )}
      </Skeleton>
    </>
  );
}

const styles = {
  productImageWrapper: css`
    display: flex;
    justify-content: center;
    align-items: center;
    border: 1px solid rgb(235, 237, 240);
  `,
  productImage: css`
    width: 100%;
    max-width: 480px;
    object-fit: contain;
    object-position: center;
    aspect-ratio: 1/1;
  `,
  productDetails: css`
    padding: 2rem 0 2rem 2rem;
  `,
  productSourceItem: css`
    cursor: pointer;
    &:hover {
      background-color: #fafafa;
      h5 {
        color: #1890ff;
      }
    }
  `,
  productSourceLink: css`
    width: 100%;
    height: 100%;
    padding: 0.8rem 0;
    display: flex;
    align-items: center;
    justify-content: space-between;
  `,
  productSourcePrice: css`
    font-size: 0.875rem;
    font-weight: 600;
    margin: 0 !important;
  `,
};
