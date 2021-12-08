import { screen } from '@testing-library/react';
import { renderWithConfig } from '../lib/test-helper';
import ProductsByBrand from './products-by-brand';
import * as reactRouter from 'react-router';

const mockProductId = '94f7ca2e-2fca-4aa1-92ec-28ceb31f773b';

describe('<Products />', () => {
  jest.mock('react-router-dom', () => ({
    useNavigate: jest.fn(),
    useSearchParams: jest.fn(),
  }));

  it('should render products page', async () => {
    jest
      .spyOn(reactRouter, 'useParams')
      .mockReturnValue({ brand: 'Alienware' } as never);

    renderWithConfig(<ProductsByBrand />);

    const productsTitle = await screen.findAllByTestId(
      /products-listing-item-title-*/,
    );
    expect(productsTitle).toHaveLength(12);

    const productTitle = await screen.findByTestId(
      `products-listing-item-title-${mockProductId}`,
    );
    const productSubtitle = await screen.findByTestId(
      `products-listing-item-subtitle-${mockProductId}`,
    );
    expect(productTitle).toHaveTextContent('ALIENWARE x17 R1');
    expect(productSubtitle).toHaveTextContent(
      'ALIENWARE x17 R1 17.3" Intel Core i7-11800H NVIDIA GeForce RTX 3060',
    );
  });
});
