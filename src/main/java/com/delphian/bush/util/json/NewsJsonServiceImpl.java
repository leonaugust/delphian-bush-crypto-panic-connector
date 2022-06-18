package com.delphian.bush.util.json;

import com.delphian.bush.dto.CryptoNewsResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.springframework.util.ResourceUtils;

import java.io.FileNotFoundException;

/**
 * Information about news with currencies:
 *
 * BTC - 7
 * RUNE - 1
 * ADA - 2
 * ETH - 2
 */
public class NewsJsonServiceImpl extends JsonToPojoService<CryptoNewsResponse> {

    static final String content = "{\n" +
            "  \"count\": 200,\n" +
            "  \"next\": \"https://cryptopanic.com/api/v1/posts/?auth_token=6924fdfcc6c55f7d46e7d3e1d3ac6294a82938e4&public=true&page=2\",\n" +
            "  \"previous\": null,\n" +
            "  \"results\": [\n" +
            "    {\n" +
            "      \"kind\": \"news\",\n" +
            "      \"domain\": \"cointelegraph.com\",\n" +
            "      \"votes\": {\n" +
            "        \"negative\": 0,\n" +
            "        \"positive\": 1,\n" +
            "        \"important\": 0,\n" +
            "        \"liked\": 0,\n" +
            "        \"disliked\": 0,\n" +
            "        \"lol\": 0,\n" +
            "        \"toxic\": 0,\n" +
            "        \"saved\": 0,\n" +
            "        \"comments\": 0\n" +
            "      },\n" +
            "      \"source\": {\n" +
            "        \"title\": \"CoinTelegraph\",\n" +
            "        \"region\": \"en\",\n" +
            "        \"domain\": \"cointelegraph.com\",\n" +
            "        \"path\": null\n" +
            "      },\n" +
            "      \"title\": \"Event recap Austin’s SXSW 2022: NFTs everywhere\",\n" +
            "      \"published_at\": \"2022-03-14T19:15:00Z\",\n" +
            "      \"slug\": \"Event-recap-Austins-SXSW-2022-NFTs-everywhere\",\n" +
            "      \"id\": 14569142,\n" +
            "      \"url\": \"https://cryptopanic.com/news/14569142/Event-recap-Austins-SXSW-2022-NFTs-everywhere\",\n" +
            "      \"created_at\": \"2022-03-14T19:15:00Z\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"kind\": \"news\",\n" +
            "      \"domain\": \"coindesk.com\",\n" +
            "      \"votes\": {\n" +
            "        \"negative\": 0,\n" +
            "        \"positive\": 1,\n" +
            "        \"important\": 0,\n" +
            "        \"liked\": 0,\n" +
            "        \"disliked\": 0,\n" +
            "        \"lol\": 0,\n" +
            "        \"toxic\": 0,\n" +
            "        \"saved\": 0,\n" +
            "        \"comments\": 1\n" +
            "      },\n" +
            "      \"source\": {\n" +
            "        \"title\": \"CoinDesk\",\n" +
            "        \"region\": \"en\",\n" +
            "        \"domain\": \"coindesk.com\",\n" +
            "        \"path\": null\n" +
            "      },\n" +
            "      \"title\": \"Bitcoin Below $39,000 After EU Vote on Crypto Regulation\",\n" +
            "      \"published_at\": \"2022-03-14T19:11:49Z\",\n" +
            "      \"slug\": \"Bitcoin-Below-39000-After-EU-Vote-on-Crypto-Regulation\",\n" +
            "      \"currencies\": [\n" +
            "        {\n" +
            "          \"code\": \"BTC\",\n" +
            "          \"title\": \"Bitcoin\",\n" +
            "          \"slug\": \"bitcoin\",\n" +
            "          \"url\": \"https://cryptopanic.com/news/bitcoin/\"\n" +
            "        }\n" +
            "      ],\n" +
            "      \"id\": 14569141,\n" +
            "      \"url\": \"https://cryptopanic.com/news/14569141/Bitcoin-Below-39000-After-EU-Vote-on-Crypto-Regulation\",\n" +
            "      \"created_at\": \"2022-03-14T19:11:49Z\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"kind\": \"news\",\n" +
            "      \"domain\": \"bitcoinist.com\",\n" +
            "      \"votes\": {\n" +
            "        \"negative\": 3,\n" +
            "        \"positive\": 2,\n" +
            "        \"important\": 0,\n" +
            "        \"liked\": 0,\n" +
            "        \"disliked\": 1,\n" +
            "        \"lol\": 2,\n" +
            "        \"toxic\": 0,\n" +
            "        \"saved\": 0,\n" +
            "        \"comments\": 2\n" +
            "      },\n" +
            "      \"source\": {\n" +
            "        \"title\": \"Bitcoinist\",\n" +
            "        \"region\": \"en\",\n" +
            "        \"domain\": \"bitcoinist.com\",\n" +
            "        \"path\": null\n" +
            "      },\n" +
            "      \"title\": \"Elon Musk Confirms He Still Holds And Won’t Sell Bitcoin, Ethereum, And Dogecoin\",\n" +
            "      \"published_at\": \"2022-03-14T19:00:51Z\",\n" +
            "      \"slug\": \"Elon-Musk-Confirms-He-Still-Holds-And-Wont-Sell-Bitcoin-Ethereum-And-Dogecoin\",\n" +
            "      \"currencies\": [\n" +
            "        {\n" +
            "          \"code\": \"BTC\",\n" +
            "          \"title\": \"Bitcoin\",\n" +
            "          \"slug\": \"bitcoin\",\n" +
            "          \"url\": \"https://cryptopanic.com/news/bitcoin/\"\n" +
            "        },\n" +
            "        {\n" +
            "          \"code\": \"ETH\",\n" +
            "          \"title\": \"Ethereum\",\n" +
            "          \"slug\": \"ethereum\",\n" +
            "          \"url\": \"https://cryptopanic.com/news/ethereum/\"\n" +
            "        },\n" +
            "        {\n" +
            "          \"code\": \"DOGE\",\n" +
            "          \"title\": \"Dogecoin\",\n" +
            "          \"slug\": \"dogecoin\",\n" +
            "          \"url\": \"https://cryptopanic.com/news/dogecoin/\"\n" +
            "        }\n" +
            "      ],\n" +
            "      \"id\": 14569109,\n" +
            "      \"url\": \"https://cryptopanic.com/news/14569109/Elon-Musk-Confirms-He-Still-Holds-And-Wont-Sell-Bitcoin-Ethereum-And-Dogecoin\",\n" +
            "      \"created_at\": \"2022-03-14T19:00:51Z\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"kind\": \"media\",\n" +
            "      \"domain\": \"youtube.com\",\n" +
            "      \"source\": {\n" +
            "        \"title\": \"Bitcoin Magazine\",\n" +
            "        \"region\": \"en\",\n" +
            "        \"domain\": \"youtube.com\",\n" +
            "        \"path\": null\n" +
            "      },\n" +
            "      \"title\": \"HAPPENING: Governmental Bitcoin Adoption\",\n" +
            "      \"published_at\": \"2022-03-14T19:00:00Z\",\n" +
            "      \"slug\": \"HAPPENING-Governmental-Bitcoin-Adoption\",\n" +
            "      \"currencies\": [\n" +
            "        {\n" +
            "          \"code\": \"BTC\",\n" +
            "          \"title\": \"Bitcoin\",\n" +
            "          \"slug\": \"bitcoin\",\n" +
            "          \"url\": \"https://cryptopanic.com/news/bitcoin/\"\n" +
            "        }\n" +
            "      ],\n" +
            "      \"id\": 14569140,\n" +
            "      \"url\": \"https://cryptopanic.com/news/14569140/HAPPENING-Governmental-Bitcoin-Adoption\",\n" +
            "      \"created_at\": \"2022-03-14T19:00:00Z\",\n" +
            "      \"votes\": {\n" +
            "        \"negative\": 0,\n" +
            "        \"positive\": 0,\n" +
            "        \"important\": 0,\n" +
            "        \"liked\": 0,\n" +
            "        \"disliked\": 0,\n" +
            "        \"lol\": 0,\n" +
            "        \"toxic\": 0,\n" +
            "        \"saved\": 0,\n" +
            "        \"comments\": 0\n" +
            "      }\n" +
            "    },\n" +
            "    {\n" +
            "      \"kind\": \"news\",\n" +
            "      \"domain\": \"beincrypto.com\",\n" +
            "      \"votes\": {\n" +
            "        \"negative\": 1,\n" +
            "        \"positive\": 0,\n" +
            "        \"important\": 0,\n" +
            "        \"liked\": 0,\n" +
            "        \"disliked\": 1,\n" +
            "        \"lol\": 1,\n" +
            "        \"toxic\": 0,\n" +
            "        \"saved\": 0,\n" +
            "        \"comments\": 0\n" +
            "      },\n" +
            "      \"source\": {\n" +
            "        \"title\": \"BeInCrypto\",\n" +
            "        \"region\": \"en\",\n" +
            "        \"domain\": \"beincrypto.com\",\n" +
            "        \"path\": null\n" +
            "      },\n" +
            "      \"title\": \"China Offers Cash Prizes in Latest Crypto Mining Clampdown\",\n" +
            "      \"published_at\": \"2022-03-14T19:00:00Z\",\n" +
            "      \"slug\": \"China-Offers-Cash-Prizes-in-Latest-Crypto-Mining-Clampdown\",\n" +
            "      \"id\": 14569153,\n" +
            "      \"url\": \"https://cryptopanic.com/news/14569153/China-Offers-Cash-Prizes-in-Latest-Crypto-Mining-Clampdown\",\n" +
            "      \"created_at\": \"2022-03-14T19:00:00Z\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"kind\": \"news\",\n" +
            "      \"domain\": \"theblockcrypto.com\",\n" +
            "      \"votes\": {\n" +
            "        \"negative\": 1,\n" +
            "        \"positive\": 0,\n" +
            "        \"important\": 0,\n" +
            "        \"liked\": 0,\n" +
            "        \"disliked\": 1,\n" +
            "        \"lol\": 1,\n" +
            "        \"toxic\": 0,\n" +
            "        \"saved\": 0,\n" +
            "        \"comments\": 0\n" +
            "      },\n" +
            "      \"source\": {\n" +
            "        \"title\": \"The Block\",\n" +
            "        \"region\": \"en\",\n" +
            "        \"domain\": \"theblockcrypto.com\",\n" +
            "        \"path\": null\n" +
            "      },\n" +
            "      \"title\": \"Rep. Brad Sherman files bill in Congress on Russian sanctions and crypto\",\n" +
            "      \"published_at\": \"2022-03-14T18:58:27Z\",\n" +
            "      \"slug\": \"Rep-Brad-Sherman-files-bill-in-Congress-on-Russian-sanctions-and-crypto\",\n" +
            "      \"id\": 14569072,\n" +
            "      \"url\": \"https://cryptopanic.com/news/14569072/Rep-Brad-Sherman-files-bill-in-Congress-on-Russian-sanctions-and-crypto\",\n" +
            "      \"created_at\": \"2022-03-14T18:58:27Z\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"kind\": \"news\",\n" +
            "      \"domain\": \"dailyhodl.com\",\n" +
            "      \"votes\": {\n" +
            "        \"negative\": 1,\n" +
            "        \"positive\": 0,\n" +
            "        \"important\": 0,\n" +
            "        \"liked\": 0,\n" +
            "        \"disliked\": 1,\n" +
            "        \"lol\": 1,\n" +
            "        \"toxic\": 0,\n" +
            "        \"saved\": 1,\n" +
            "        \"comments\": 0\n" +
            "      },\n" +
            "      \"source\": {\n" +
            "        \"title\": \"The Daily Hodl\",\n" +
            "        \"region\": \"en\",\n" +
            "        \"domain\": \"dailyhodl.com\",\n" +
            "        \"path\": null\n" +
            "      },\n" +
            "      \"title\": \"Department of Justice Targets Crypto Exchanges in Effort To Clamp Down on Russian Oligarchs: Report\",\n" +
            "      \"published_at\": \"2022-03-14T18:55:54Z\",\n" +
            "      \"slug\": \"Department-of-Justice-Targets-Crypto-Exchanges-in-Effort-To-Clamp-Down-on-Russian-Oligarchs-Report\",\n" +
            "      \"id\": 14569150,\n" +
            "      \"url\": \"https://cryptopanic.com/news/14569150/Department-of-Justice-Targets-Crypto-Exchanges-in-Effort-To-Clamp-Down-on-Russian-Oligarchs-Report\",\n" +
            "      \"created_at\": \"2022-03-14T18:55:54Z\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"kind\": \"news\",\n" +
            "      \"domain\": \"blockworks.co\",\n" +
            "      \"votes\": {\n" +
            "        \"negative\": 0,\n" +
            "        \"positive\": 1,\n" +
            "        \"important\": 0,\n" +
            "        \"liked\": 1,\n" +
            "        \"disliked\": 0,\n" +
            "        \"lol\": 0,\n" +
            "        \"toxic\": 0,\n" +
            "        \"saved\": 0,\n" +
            "        \"comments\": 0\n" +
            "      },\n" +
            "      \"source\": {\n" +
            "        \"title\": \"Blockworks\",\n" +
            "        \"region\": \"en\",\n" +
            "        \"domain\": \"blockworks.co\",\n" +
            "        \"path\": null\n" +
            "      },\n" +
            "      \"title\": \"Illinois Moves Closer To Accepting Crypto for Taxes, but Not Considering Legal Tender Status\",\n" +
            "      \"published_at\": \"2022-03-14T18:50:52Z\",\n" +
            "      \"slug\": \"Illinois-Moves-Closer-To-Accepting-Crypto-for-Taxes-but-Not-Considering-Legal-Tender-Status\",\n" +
            "      \"id\": 14569056,\n" +
            "      \"url\": \"https://cryptopanic.com/news/14569056/Illinois-Moves-Closer-To-Accepting-Crypto-for-Taxes-but-Not-Considering-Legal-Tender-Status\",\n" +
            "      \"created_at\": \"2022-03-14T18:50:52Z\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"kind\": \"media\",\n" +
            "      \"domain\": \"youtube.com\",\n" +
            "      \"votes\": {\n" +
            "        \"negative\": 1,\n" +
            "        \"positive\": 0,\n" +
            "        \"important\": 0,\n" +
            "        \"liked\": 0,\n" +
            "        \"disliked\": 0,\n" +
            "        \"lol\": 0,\n" +
            "        \"toxic\": 0,\n" +
            "        \"saved\": 1,\n" +
            "        \"comments\": 0\n" +
            "      },\n" +
            "      \"source\": {\n" +
            "        \"title\": \"Ian Balina\",\n" +
            "        \"region\": \"en\",\n" +
            "        \"domain\": \"youtube.com\",\n" +
            "        \"path\": null\n" +
            "      },\n" +
            "      \"title\": \"When Will Crypto Go Back Up? (LUNA, ZEC, RUNE Price Predictions) | Market Update\",\n" +
            "      \"published_at\": \"2022-03-14T18:45:47Z\",\n" +
            "      \"slug\": \"When-Will-Crypto-Go-Back-Up-LUNA-ZEC-RUNE-Price-Predictions-Market-Update\",\n" +
            "      \"currencies\": [\n" +
            "        {\n" +
            "          \"code\": \"RUNE\",\n" +
            "          \"title\": \"THORChain\",\n" +
            "          \"slug\": \"thorchain\",\n" +
            "          \"url\": \"https://cryptopanic.com/news/thorchain/\"\n" +
            "        }\n" +
            "      ],\n" +
            "      \"id\": 14568810,\n" +
            "      \"url\": \"https://cryptopanic.com/news/14568810/When-Will-Crypto-Go-Back-Up-LUNA-ZEC-RUNE-Price-Predictions-Market-Update\",\n" +
            "      \"created_at\": \"2022-03-14T18:45:47Z\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"kind\": \"news\",\n" +
            "      \"domain\": \"dailyhodl.com\",\n" +
            "      \"source\": {\n" +
            "        \"title\": \"The Daily Hodl\",\n" +
            "        \"region\": \"en\",\n" +
            "        \"domain\": \"dailyhodl.com\",\n" +
            "        \"path\": null\n" +
            "      },\n" +
            "      \"title\": \"Institutions Trim Down Crypto Investments Amid Regulatory and Geopolitical Uncertainty: CoinShares\",\n" +
            "      \"published_at\": \"2022-03-14T18:41:52Z\",\n" +
            "      \"slug\": \"Institutions-Trim-Down-Crypto-Investments-Amid-Regulatory-and-Geopolitical-Uncertainty-CoinShares\",\n" +
            "      \"currencies\": [\n" +
            "        {\n" +
            "          \"code\": \"BTC\",\n" +
            "          \"title\": \"Bitcoin\",\n" +
            "          \"slug\": \"bitcoin\",\n" +
            "          \"url\": \"https://cryptopanic.com/news/bitcoin/\"\n" +
            "        },\n" +
            "        {\n" +
            "          \"code\": \"ETH\",\n" +
            "          \"title\": \"Ethereum\",\n" +
            "          \"slug\": \"ethereum\",\n" +
            "          \"url\": \"https://cryptopanic.com/news/ethereum/\"\n" +
            "        }\n" +
            "      ],\n" +
            "      \"id\": 14569149,\n" +
            "      \"url\": \"https://cryptopanic.com/news/14569149/Institutions-Trim-Down-Crypto-Investments-Amid-Regulatory-and-Geopolitical-Uncertainty-CoinShares\",\n" +
            "      \"created_at\": \"2022-03-14T18:41:52Z\",\n" +
            "      \"votes\": {\n" +
            "        \"negative\": 0,\n" +
            "        \"positive\": 0,\n" +
            "        \"important\": 0,\n" +
            "        \"liked\": 0,\n" +
            "        \"disliked\": 0,\n" +
            "        \"lol\": 0,\n" +
            "        \"toxic\": 0,\n" +
            "        \"saved\": 0,\n" +
            "        \"comments\": 0\n" +
            "      }\n" +
            "    },\n" +
            "    {\n" +
            "      \"kind\": \"news\",\n" +
            "      \"domain\": \"zycrypto.com\",\n" +
            "      \"votes\": {\n" +
            "        \"negative\": 0,\n" +
            "        \"positive\": 6,\n" +
            "        \"important\": 3,\n" +
            "        \"liked\": 2,\n" +
            "        \"disliked\": 0,\n" +
            "        \"lol\": 1,\n" +
            "        \"toxic\": 0,\n" +
            "        \"saved\": 0,\n" +
            "        \"comments\": 4\n" +
            "      },\n" +
            "      \"source\": {\n" +
            "        \"title\": \"ZyCrypto\",\n" +
            "        \"region\": \"en\",\n" +
            "        \"domain\": \"zycrypto.com\",\n" +
            "        \"path\": null\n" +
            "      },\n" +
            "      \"title\": \"Crypto Proponents Bet On Cardano To Surpass $5 This Year, But What Are The Odds Of A 500% Upsurge?\",\n" +
            "      \"published_at\": \"2022-03-14T18:40:47Z\",\n" +
            "      \"slug\": \"Crypto-Proponents-Bet-On-Cardano-To-Surpass-5-This-Year-But-What-Are-The-Odds-Of-A-500-Upsurge\",\n" +
            "      \"currencies\": [\n" +
            "        {\n" +
            "          \"code\": \"ADA\",\n" +
            "          \"title\": \"Cardano\",\n" +
            "          \"slug\": \"cardano\",\n" +
            "          \"url\": \"https://cryptopanic.com/news/cardano/\"\n" +
            "        }\n" +
            "      ],\n" +
            "      \"id\": 14569040,\n" +
            "      \"url\": \"https://cryptopanic.com/news/14569040/Crypto-Proponents-Bet-On-Cardano-To-Surpass-5-This-Year-But-What-Are-The-Odds-Of-A-500-Upsurge\",\n" +
            "      \"created_at\": \"2022-03-14T18:40:47Z\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"kind\": \"news\",\n" +
            "      \"domain\": \"cointelegraph.com\",\n" +
            "      \"votes\": {\n" +
            "        \"negative\": 1,\n" +
            "        \"positive\": 0,\n" +
            "        \"important\": 0,\n" +
            "        \"liked\": 0,\n" +
            "        \"disliked\": 1,\n" +
            "        \"lol\": 1,\n" +
            "        \"toxic\": 0,\n" +
            "        \"saved\": 0,\n" +
            "        \"comments\": 0\n" +
            "      },\n" +
            "      \"source\": {\n" +
            "        \"title\": \"CoinTelegraph\",\n" +
            "        \"region\": \"en\",\n" +
            "        \"domain\": \"cointelegraph.com\",\n" +
            "        \"path\": null\n" +
            "      },\n" +
            "      \"title\": \"Here’s how Asian countries deal with crypto sanctions against Russia\",\n" +
            "      \"published_at\": \"2022-03-14T18:30:00Z\",\n" +
            "      \"slug\": \"Heres-how-Asian-countries-deal-with-crypto-sanctions-against-Russia\",\n" +
            "      \"id\": 14568973,\n" +
            "      \"url\": \"https://cryptopanic.com/news/14568973/Heres-how-Asian-countries-deal-with-crypto-sanctions-against-Russia\",\n" +
            "      \"created_at\": \"2022-03-14T18:30:00Z\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"kind\": \"news\",\n" +
            "      \"domain\": \"coindesk.com\",\n" +
            "      \"votes\": {\n" +
            "        \"negative\": 1,\n" +
            "        \"positive\": 1,\n" +
            "        \"important\": 1,\n" +
            "        \"liked\": 0,\n" +
            "        \"disliked\": 0,\n" +
            "        \"lol\": 0,\n" +
            "        \"toxic\": 0,\n" +
            "        \"saved\": 0,\n" +
            "        \"comments\": 1\n" +
            "      },\n" +
            "      \"source\": {\n" +
            "        \"title\": \"CoinDesk\",\n" +
            "        \"region\": \"en\",\n" +
            "        \"domain\": \"coindesk.com\",\n" +
            "        \"path\": null\n" +
            "      },\n" +
            "      \"title\": \"The First NFT Monopoly\",\n" +
            "      \"published_at\": \"2022-03-14T18:21:53Z\",\n" +
            "      \"slug\": \"The-First-NFT-Monopoly\",\n" +
            "      \"id\": 14568970,\n" +
            "      \"url\": \"https://cryptopanic.com/news/14568970/The-First-NFT-Monopoly\",\n" +
            "      \"created_at\": \"2022-03-14T18:21:53Z\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"kind\": \"news\",\n" +
            "      \"domain\": \"coindesk.com\",\n" +
            "      \"votes\": {\n" +
            "        \"negative\": 0,\n" +
            "        \"positive\": 2,\n" +
            "        \"important\": 1,\n" +
            "        \"liked\": 1,\n" +
            "        \"disliked\": 0,\n" +
            "        \"lol\": 0,\n" +
            "        \"toxic\": 0,\n" +
            "        \"saved\": 1,\n" +
            "        \"comments\": 0\n" +
            "      },\n" +
            "      \"source\": {\n" +
            "        \"title\": \"CoinDesk\",\n" +
            "        \"region\": \"en\",\n" +
            "        \"domain\": \"coindesk.com\",\n" +
            "        \"path\": null\n" +
            "      },\n" +
            "      \"title\": \"Bitcoin Range-Bound Above $35K-$37K Support; Resistance at $40K\",\n" +
            "      \"published_at\": \"2022-03-14T18:19:29Z\",\n" +
            "      \"slug\": \"Bitcoin-Range-Bound-Above-35K-37K-Support-Resistance-at-40K\",\n" +
            "      \"currencies\": [\n" +
            "        {\n" +
            "          \"code\": \"BTC\",\n" +
            "          \"title\": \"Bitcoin\",\n" +
            "          \"slug\": \"bitcoin\",\n" +
            "          \"url\": \"https://cryptopanic.com/news/bitcoin/\"\n" +
            "        }\n" +
            "      ],\n" +
            "      \"id\": 14568971,\n" +
            "      \"url\": \"https://cryptopanic.com/news/14568971/Bitcoin-Range-Bound-Above-35K-37K-Support-Resistance-at-40K\",\n" +
            "      \"created_at\": \"2022-03-14T18:19:29Z\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"kind\": \"news\",\n" +
            "      \"domain\": \"cryptoslate.com\",\n" +
            "      \"votes\": {\n" +
            "        \"negative\": 1,\n" +
            "        \"positive\": 2,\n" +
            "        \"important\": 1,\n" +
            "        \"liked\": 1,\n" +
            "        \"disliked\": 0,\n" +
            "        \"lol\": 1,\n" +
            "        \"toxic\": 0,\n" +
            "        \"saved\": 0,\n" +
            "        \"comments\": 0\n" +
            "      },\n" +
            "      \"source\": {\n" +
            "        \"title\": \"CryptoSlate\",\n" +
            "        \"region\": \"en\",\n" +
            "        \"domain\": \"cryptoslate.com\",\n" +
            "        \"path\": null\n" +
            "      },\n" +
            "      \"title\": \"Here’s why the SEC’s last decision on Ripple’s lawsuit is a win\",\n" +
            "      \"published_at\": \"2022-03-14T18:10:05Z\",\n" +
            "      \"slug\": \"Heres-why-the-SECs-last-decision-on-Ripples-lawsuit-is-a-win\",\n" +
            "      \"id\": 14568984,\n" +
            "      \"url\": \"https://cryptopanic.com/news/14568984/Heres-why-the-SECs-last-decision-on-Ripples-lawsuit-is-a-win\",\n" +
            "      \"created_at\": \"2022-03-14T18:10:05Z\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"kind\": \"media\",\n" +
            "      \"domain\": \"youtube.com\",\n" +
            "      \"votes\": {\n" +
            "        \"negative\": 0,\n" +
            "        \"positive\": 1,\n" +
            "        \"important\": 0,\n" +
            "        \"liked\": 0,\n" +
            "        \"disliked\": 0,\n" +
            "        \"lol\": 0,\n" +
            "        \"toxic\": 0,\n" +
            "        \"saved\": 0,\n" +
            "        \"comments\": 0\n" +
            "      },\n" +
            "      \"source\": {\n" +
            "        \"title\": \"Bitcoin Magazine\",\n" +
            "        \"region\": \"en\",\n" +
            "        \"domain\": \"youtube.com\",\n" +
            "        \"path\": null\n" +
            "      },\n" +
            "      \"title\": \"The Bitcoin Challenge | Street Interviews!\",\n" +
            "      \"published_at\": \"2022-03-14T18:05:42Z\",\n" +
            "      \"slug\": \"The-Bitcoin-Challenge-Street-Interviews\",\n" +
            "      \"currencies\": [\n" +
            "        {\n" +
            "          \"code\": \"BTC\",\n" +
            "          \"title\": \"Bitcoin\",\n" +
            "          \"slug\": \"bitcoin\",\n" +
            "          \"url\": \"https://cryptopanic.com/news/bitcoin/\"\n" +
            "        }\n" +
            "      ],\n" +
            "      \"id\": 14568911,\n" +
            "      \"url\": \"https://cryptopanic.com/news/14568911/The-Bitcoin-Challenge-Street-Interviews\",\n" +
            "      \"created_at\": \"2022-03-14T18:05:42Z\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"kind\": \"news\",\n" +
            "      \"domain\": \"dailyhodl.com\",\n" +
            "      \"votes\": {\n" +
            "        \"negative\": 0,\n" +
            "        \"positive\": 1,\n" +
            "        \"important\": 1,\n" +
            "        \"liked\": 0,\n" +
            "        \"disliked\": 0,\n" +
            "        \"lol\": 0,\n" +
            "        \"toxic\": 0,\n" +
            "        \"saved\": 0,\n" +
            "        \"comments\": 0\n" +
            "      },\n" +
            "      \"source\": {\n" +
            "        \"title\": \"The Daily Hodl\",\n" +
            "        \"region\": \"en\",\n" +
            "        \"domain\": \"dailyhodl.com\",\n" +
            "        \"path\": null\n" +
            "      },\n" +
            "      \"title\": \"Enterprise Blockchain Altcoin Built on Ethereum Quickly Skyrockets 70% Days Before Mainnet Launch\",\n" +
            "      \"published_at\": \"2022-03-14T18:05:33Z\",\n" +
            "      \"slug\": \"Enterprise-Blockchain-Altcoin-Built-on-Ethereum-Quickly-Skyrockets-70-Days-Before-Mainnet-Launch\",\n" +
            "      \"currencies\": [\n" +
            "        {\n" +
            "          \"code\": \"ETH\",\n" +
            "          \"title\": \"Ethereum\",\n" +
            "          \"slug\": \"ethereum\",\n" +
            "          \"url\": \"https://cryptopanic.com/news/ethereum/\"\n" +
            "        }\n" +
            "      ],\n" +
            "      \"id\": 14568949,\n" +
            "      \"url\": \"https://cryptopanic.com/news/14568949/Enterprise-Blockchain-Altcoin-Built-on-Ethereum-Quickly-Skyrockets-70-Days-Before-Mainnet-Launch\",\n" +
            "      \"created_at\": \"2022-03-14T18:05:33Z\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"kind\": \"news\",\n" +
            "      \"domain\": \"theblockcrypto.com\",\n" +
            "      \"votes\": {\n" +
            "        \"negative\": 0,\n" +
            "        \"positive\": 9,\n" +
            "        \"important\": 1,\n" +
            "        \"liked\": 2,\n" +
            "        \"disliked\": 0,\n" +
            "        \"lol\": 0,\n" +
            "        \"toxic\": 0,\n" +
            "        \"saved\": 1,\n" +
            "        \"comments\": 1\n" +
            "      },\n" +
            "      \"source\": {\n" +
            "        \"title\": \"The Block\",\n" +
            "        \"region\": \"en\",\n" +
            "        \"domain\": \"theblockcrypto.com\",\n" +
            "        \"path\": null\n" +
            "      },\n" +
            "      \"title\": \"Europe’s landmark crypto bill passes parliamentary committee with wide majority\",\n" +
            "      \"published_at\": \"2022-03-14T18:03:41Z\",\n" +
            "      \"slug\": \"Europes-landmark-crypto-bill-passes-parliamentary-committee-with-wide-majority\",\n" +
            "      \"currencies\": [\n" +
            "        {\n" +
            "          \"code\": \"BTC\",\n" +
            "          \"title\": \"Bitcoin\",\n" +
            "          \"slug\": \"bitcoin\",\n" +
            "          \"url\": \"https://cryptopanic.com/news/bitcoin/\"\n" +
            "        }\n" +
            "      ],\n" +
            "      \"id\": 14568921,\n" +
            "      \"url\": \"https://cryptopanic.com/news/14568921/Europes-landmark-crypto-bill-passes-parliamentary-committee-with-wide-majority\",\n" +
            "      \"created_at\": \"2022-03-14T18:03:41Z\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"kind\": \"news\",\n" +
            "      \"domain\": \"cryptoglobe.com\",\n" +
            "      \"votes\": {\n" +
            "        \"negative\": 0,\n" +
            "        \"positive\": 6,\n" +
            "        \"important\": 2,\n" +
            "        \"liked\": 2,\n" +
            "        \"disliked\": 0,\n" +
            "        \"lol\": 0,\n" +
            "        \"toxic\": 0,\n" +
            "        \"saved\": 0,\n" +
            "        \"comments\": 1\n" +
            "      },\n" +
            "      \"source\": {\n" +
            "        \"title\": \"CryptoGlobe\",\n" +
            "        \"region\": \"en\",\n" +
            "        \"domain\": \"cryptoglobe.com\",\n" +
            "        \"path\": null\n" +
            "      },\n" +
            "      \"title\": \"Cardano ($ADA) Founder on When the Next ‘Large Wave’ of DApps Will Come\",\n" +
            "      \"published_at\": \"2022-03-14T18:02:48Z\",\n" +
            "      \"slug\": \"Cardano-ADA-Founder-on-When-the-Next-Large-Wave-of-DApps-Will-Come\",\n" +
            "      \"currencies\": [\n" +
            "        {\n" +
            "          \"code\": \"ADA\",\n" +
            "          \"title\": \"Cardano\",\n" +
            "          \"slug\": \"cardano\",\n" +
            "          \"url\": \"https://cryptopanic.com/news/cardano/\"\n" +
            "        }\n" +
            "      ],\n" +
            "      \"id\": 14568915,\n" +
            "      \"url\": \"https://cryptopanic.com/news/14568915/Cardano-ADA-Founder-on-When-the-Next-Large-Wave-of-DApps-Will-Come\",\n" +
            "      \"created_at\": \"2022-03-14T18:02:48Z\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"kind\": \"news\",\n" +
            "      \"domain\": \"decrypt.co\",\n" +
            "      \"votes\": {\n" +
            "        \"negative\": 1,\n" +
            "        \"positive\": 1,\n" +
            "        \"important\": 0,\n" +
            "        \"liked\": 1,\n" +
            "        \"disliked\": 0,\n" +
            "        \"lol\": 0,\n" +
            "        \"toxic\": 0,\n" +
            "        \"saved\": 0,\n" +
            "        \"comments\": 0\n" +
            "      },\n" +
            "      \"source\": {\n" +
            "        \"title\": \"Decrypt\",\n" +
            "        \"region\": \"en\",\n" +
            "        \"domain\": \"decrypt.co\",\n" +
            "        \"path\": null\n" +
            "      },\n" +
            "      \"title\": \"What is Arbitrum? Speeding Up Ethereum Using Optimistic Rollups\",\n" +
            "      \"published_at\": \"2022-03-14T18:02:32Z\",\n" +
            "      \"slug\": \"What-is-Arbitrum-Speeding-Up-Ethereum-Using-Optimistic-Rollups\",\n" +
            "      \"currencies\": [\n" +
            "        {\n" +
            "          \"code\": \"ETH\",\n" +
            "          \"title\": \"Ethereum\",\n" +
            "          \"slug\": \"ethereum\",\n" +
            "          \"url\": \"https://cryptopanic.com/news/ethereum/\"\n" +
            "        }\n" +
            "      ],\n" +
            "      \"id\": 14568918,\n" +
            "      \"url\": \"https://cryptopanic.com/news/14568918/What-is-Arbitrum-Speeding-Up-Ethereum-Using-Optimistic-Rollups\",\n" +
            "      \"created_at\": \"2022-03-14T18:02:32Z\"\n" +
            "    }\n" +
            "  ]\n" +
            "}";

    public NewsJsonServiceImpl(ObjectMapper objectMapper) throws FileNotFoundException {
        super(content, objectMapper, CryptoNewsResponse.class);
    }

}
