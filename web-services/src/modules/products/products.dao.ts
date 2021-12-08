import { getDbConnection } from '../../common/database';
import { IBrand, IProduct, IRawProduct } from './products.interface';
import { reformatProductSources } from '../../lib/mysql-util';
import { IPagination } from '../../shared/interfaces/pagination.interface';
import { NotFoundException } from '../../errors/http-exceptions';

const db = getDbConnection();

export const findAllProducts = async (
  pagination: IPagination,
): Promise<IProduct[]> => {
  const { perPage, offset } = pagination;
  const sqlQuery =
    'SELECT ls.id, b.name AS brand, l.name, l.image_url AS imageUrl, l.screen_size AS screenSize, s.processor, s.graphics_card AS graphicsCard, s.ram, s.hdd, s.ssd ' +
    'FROM `laptop_specification` ls ' +
    'INNER JOIN `laptops` l ON ls.laptop_id = l.id ' +
    'INNER JOIN `brands` b ON l.brand_id = b.id ' +
    'INNER JOIN `specifications` s ON ls.specification_id = s.id ' +
    'LIMIT ? OFFSET ?';
  const [result] = await db.execute(sqlQuery, [perPage, offset?.toString()]);
  return result as IProduct[];
};

export const findAllProductsBrand = async () => {
  const sqlQuery = 'SELECT * FROM `brands`';
  const [result] = await db.execute(sqlQuery);
  return result as IBrand[];
};

export const findProduct = async (id: string): Promise<IProduct | null> => {
  const sqlQuery =
    'SELECT ls.id, b.name AS brand, l.name, l.image_url AS imageUrl, l.screen_size AS screenSize, spec.processor, spec.graphics_card AS graphicsCard, spec.ram, spec.hdd, spec.ssd, ' +
    'GROUP_CONCAT(s.id) AS sourcesId, GROUP_CONCAT(s.website) AS sourcesWebsite, GROUP_CONCAT(s.price) AS sourcesPrice, GROUP_CONCAT(s.url) AS sourcesUrl ' +
    'FROM (`laptop_specification` ls JOIN sources s ON s.laptop_specification_id = ? ) ' +
    'INNER JOIN `laptops` l ON ls.laptop_id = l.id ' +
    'INNER JOIN `brands` b ON l.brand_id = b.id ' +
    'INNER JOIN `specifications` spec ON ls.specification_id = spec.id ' +
    'WHERE ls.id = ? ' +
    'GROUP BY ls.id';

  const [result] = await db.execute(sqlQuery, [id, id]);
  const products = <IRawProduct[]>result;
  if (!products.length) throw new NotFoundException('Product not founded');
  return reformatProductSources(products[0]);
};

export const findProductCount = async (): Promise<number> => {
  const sqlQuery = 'SELECT COUNT(*) FROM `laptop_specification`';
  const [result] = await db.execute(sqlQuery);
  return (result as Record<string, number>[])[0]['COUNT(*)'];
};

export const findProductCountByBrand = async (
  keywords: string,
): Promise<number> => {
  const sqlQuery =
    'SELECT COUNT(*) ' +
    'FROM `brands` b ' +
    'RIGHT JOIN `laptops` l ON l.brand_id = b.id ' +
    'RIGHT JOIN `laptop_specification` ls ON ls.laptop_id = l.id ' +
    `WHERE b.name LIKE '${keywords}%'`;

  const [result] = await db.execute(sqlQuery);
  return (result as Record<string, number>[])[0]['COUNT(*)'];
};

export const findProductsByBrand = async (
  keywords: string,
  pagination: IPagination,
): Promise<IProduct[]> => {
  const { perPage, offset } = pagination;
  const sqlQuery =
    'SELECT ls.id, b.name AS brand, l.name, l.image_url AS imageUrl, l.screen_size AS screenSize, spec.processor, spec.graphics_card AS graphicsCard, spec.ram, spec.hdd, spec.ssd ' +
    'FROM `brands` b ' +
    'RIGHT JOIN `laptops` l ON l.brand_id = b.id ' +
    'RIGHT JOIN `laptop_specification` ls ON ls.laptop_id = l.id ' +
    'INNER JOIN `specifications` spec ON spec.id = ls.specification_id ' +
    `WHERE b.name LIKE '${keywords}%' ` +
    'LIMIT ? OFFSET ?';

  const [result] = await db.execute(sqlQuery, [perPage, offset.toString()]);
  return result as IProduct[];
};
