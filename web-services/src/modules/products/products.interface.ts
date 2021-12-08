export interface IProduct {
  id: string;
  brand: string;
  name: string;
  imageUrl: string;
  processor: string;
  graphicsCard: string;
  ssd?: string;
  hdd?: string;
  ram: string;
  screenSize: string;
  sources?: IProductSource[];
}

export interface IRawProduct extends IProduct {
  sourcesId?: string;
  sourcesWebsite?: string;
  sourcesPrice?: string;
  sourcesUrl?: string;
}

export interface IProductSource {
  id: string;
  website: string;
  url: string;
  price: number;
}

export interface IBrand {
  id: string;
  name: string;
}
