import { css } from '@emotion/css';
import { Button } from 'antd';
import Container from './container';
import { Link } from 'react-router-dom';

export default function Navbar() {
  return (
    <nav className={styles.base}>
      <Container>
        <div className={styles.content}>
          <h1 className={styles.logo}>Prices</h1>
          <Link to="/">
            <Button type="primary">Home</Button>
          </Link>
        </div>
      </Container>
    </nav>
  );
}

const styles = {
  base: css`
    height: auto;
    padding: 1rem 0;
    position: sticky;
    top: 0;
    z-index: 50;
    border-bottom: 1px solid rgb(235, 237, 240);
    background-color: white;
  `,
  content: css`
    height: 100%;
    display: flex;
    justify-content: space-between;
    align-items: center;
  `,
  logo: css`
    margin: 0;
    text-align: center;
    @media (min-width: 576px) {
      text-align: left;
    }
  `,
};
