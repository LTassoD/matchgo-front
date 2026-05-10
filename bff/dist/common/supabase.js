"use strict";
Object.defineProperty(exports, "__esModule", { value: true });
exports.getAnonClient = exports.getServerClient = void 0;
const supabase_js_1 = require("@supabase/supabase-js");
const getServerClient = () => {
    return (0, supabase_js_1.createClient)(process.env.SUPABASE_URL, process.env.SUPABASE_SERVICE_ROLE_KEY);
};
exports.getServerClient = getServerClient;
const getAnonClient = () => {
    return (0, supabase_js_1.createClient)(process.env.SUPABASE_URL, process.env.SUPABASE_ANON_KEY);
};
exports.getAnonClient = getAnonClient;
//# sourceMappingURL=supabase.js.map