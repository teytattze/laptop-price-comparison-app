import { screen } from '@testing-library/react';
import { renderWithConfig } from '../lib/test-helper';
import Products from './products';

const mockProductId = '287eec84-ffd4-4779-9951-624f782b4fd4';

describe('<Products />', () => {
  jest.mock('react-router-dom', () => ({
    useNavigate: jest.fn(),
    useSearchParams: jest.fn(),
  }));

  it('should render products page', async () => {
    renderWithConfig(<Products />);

    const productsTitle = await screen.findAllByTestId(
      /products-listing-item-title-*/,
    );
    expect(productsTitle).toHaveLength(20);

    const productTitle = await screen.findByTestId(
      `products-listing-item-title-${mockProductId}`,
    );
    const productSubtitle = await screen.findByTestId(
      `products-listing-item-subtitle-${mockProductId}`,
    );
    expect(productTitle).toHaveTextContent('ACER Predator Triton 300');
    expect(productSubtitle).toHaveTextContent(
      'ACER Predator Triton 300 15.6" Intel Core i7-11800H NVIDIA GeForce RTX 3060',
    );
  });
});
