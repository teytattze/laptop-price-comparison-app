import { screen } from '@testing-library/react';
import * as reactRouter from 'react-router';
import { renderWithConfig } from '../lib/test-helper';
import Product from './product';

const mockProductId = 'a7891e3a-b18e-4952-90ec-efb96d33fb6d';

describe('<Product />', () => {
  jest.mock('react-router-dom', () => ({
    useNavigate: jest.fn(),
  }));

  it('should render product page', async () => {
    jest
      .spyOn(reactRouter, 'useParams')
      .mockReturnValue({ id: mockProductId } as never);

    renderWithConfig(<Product />);
    const productTitle = await screen.findByTestId(/product-title/);
    const productSubtitle = await screen.findByTestId(/product-subtitle/);
    expect(productTitle).toHaveTextContent('ASUS TUF 15.6"');
    expect(productSubtitle).toHaveTextContent(
      'AMD Ryzen 5 3550H, NVIDIA GeForce GTX 1650, 8 GB RAM, 512 GB SSD',
    );

    const productSourceAo = await screen.findByTestId(/product-source-link-Ao/);
    const productSourceBox = await screen.findByTestId(
      /product-source-link-Box/,
    );
    expect(productSourceAo).toBeDefined();
    expect(productSourceBox).toBeDefined();
  });
});
