import {
  IProduct,
  IProductSource,
  IRawProduct,
} from '../modules/products/products.interface';

export const reformatProductSources = (product: IRawProduct): IProduct => {
  const sources: IProductSource[] = [];
  const sourcesId = product.sourcesId?.split(',');
  const sourcesWebsite = product.sourcesWebsite?.split(',');
  const sourcesPrice = product.sourcesPrice?.split(',');
  const sourcesUrl = product.sourcesUrl?.split(',');

  for (let i = 0; sourcesId && i < sourcesId.length; i++) {
    const sourceObj: IProductSource = {
      id: sourcesId[i],
      website: sourcesWebsite![i],
      price: Number(sourcesPrice![i]),
      url: sourcesUrl![i],
    };
    sources.push(sourceObj);
  }

  const result = { ...product, sources: sources };
  delete result.sourcesId;
  delete result.sourcesWebsite;
  delete result.sourcesUrl;
  delete result.sourcesPrice;

  return result;
};
