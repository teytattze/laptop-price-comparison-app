import mysql from 'mysql2';
import bluebird from 'bluebird';
import { config } from './config';

const conn = mysql
  .createPool({
    host: config.mysql.host,
    user: config.mysql.user,
    password: config.mysql.password,
    database: config.mysql.database,
    connectionLimit: config.mysql.connectionLimit as number | undefined,
    Promise: bluebird,
  })
  .promise();

export const getDbConnection = () => {
  return conn;
};
