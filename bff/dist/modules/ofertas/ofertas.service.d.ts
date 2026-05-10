export declare class OfertasService {
    create(body: any): Promise<any>;
    findOne(id: string): Promise<any>;
    list(filters: any): Promise<{
        data: any[] | null;
        total: number;
        page: number;
        limit: number;
        totalPages: number;
    }>;
    update(id: string, body: any): Promise<any>;
    remove(id: string): Promise<{
        message: string;
    }>;
}
