import { css } from '@emotion/css';
import React from 'react';

type ContainerProps = {
  children: React.ReactNode;
};

export default function Container({ children }: ContainerProps) {
  return <div className={styles.base}>{children}</div>;
}

const styles = {
  base: css`
    height: 100%;
    max-width: 1200px;
    margin: 0 auto;
    padding: 0 1rem;
  `,
};
