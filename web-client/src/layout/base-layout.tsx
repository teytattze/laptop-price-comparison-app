import { Outlet } from 'react-router-dom';
import Navbar from '../components/navbar';
import Container from '../components/container';
import { css } from '@emotion/css';

export default function BaseLayout() {
  return (
    <>
      <Navbar />
      <Container>
        <div className={styles.base}>
          <Outlet />
        </div>
      </Container>
    </>
  );
}

const styles = {
  base: css`
    padding: 1rem 0;
  `,
};
