import { renderWithConfig } from '../lib/test-helper';
import Home from './home';
import { screen } from '@testing-library/react';

describe('<Home />', () => {
  it('should render ui', async () => {
    renderWithConfig(<Home />);
    const header = await screen.findAllByTestId('home-header');
    const form = await screen.findAllByTestId('home-form');
    expect(header).toBeDefined();
    expect(form).toBeDefined();
  });
});
