package com.ning.ybsxpss.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.ning.ybsxpss.R;
import com.ning.ybsxpss.util.Utility;

public class HtmlActivity extends AppCompatActivity {
    private TextView tv_html;
    private ImageView iv_html_back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_html);
        Utility.setToolbar(this);
        tv_html = (TextView) findViewById(R.id.html);
        iv_html_back = (ImageView) findViewById(R.id.iv_html_back);

        String str = "<p>\n" +
                "    本协议是您与校园食安网站（简称“本站”，网址：www.yuanbenfresh.com.cn）所有者（以下简称为“校园食安”）之间就本站站服务等相关事宜所订立的契约，请您仔细阅读本注册协议，您点击“同意以下协议”按钮后，本协议即构成对双方有约束力的法律文件\n" +
                "</p>\n" +
                "<p>\n" +
                "    <br/>\n" +
                "</p>\n" +
                "<p>\n" +
                "    本站的各项电子服务的所有权和运作权归校园食安所有。用户同意所有注册协议条款并完成注册程序，才能成为本站的正式用户。用户确认：本协议条款是处理双方权利义务的契约，始终有效，法律另有强制性规定或双方另有特别约定的，依其规定。\n" +
                "</p>\n" +
                "<p>\n" +
                "    <br/>\n" +
                "</p>\n" +
                "<p>\n" +
                "    用户点击同意本协议的，即视为用户确认自己具有享受本站服务、下单购物等相应的权利能力和行为能力，能够独立承担法律责任。\n" +
                "</p>\n" +
                "<p>\n" +
                "    <br/>\n" +
                "</p>\n" +
                "<p>\n" +
                "    本站有权根据需要不时地制订、修改本协议及/或各类规则，并以网站公示的方式进行公告，不再单独通知您。修订后的协议或将来可能发布或更新的各类规则一经在网站公布后，立即自动生效。如您不同意相关修订，应当立即停止使用本站服务。您继续使用本站服务，即表示您接受经修订的协议或规则。\n" +
                "</p>\n" +
                "<p>\n" +
                "    <br/>\n" +
                "</p>\n" +
                "<p>\n" +
                "    一、 本站账户：\n" +
                "</p>\n" +
                "<p>\n" +
                "    1、 用户在注册成为本站用户时提供的信息应真实、完整、有效，并保证本站可以通过该信息与用户本人进行联系。同时，用户也有义务在相关资料发生变更时及时更新注册资信息。\n" +
                "</p>\n" +
                "<p>\n" +
                "    <br/>\n" +
                "</p>\n" +
                "<p>\n" +
                "    2、 在成功注册后，本站会为每位用户开通一个账户，作为其使用本站服务的唯一身份标识，用户应妥善保管该账户的用户名和密码，并对在其账户和密码下发生的所有活动承担责任。\n" +
                "</p>\n" +
                "<p>\n" +
                "    <br/>\n" +
                "</p>\n" +
                "<p>\n" +
                "    3、 本站用户必须是具有合法经营资格的实体组织,并提交电子及纸质资质用于审核及备案。\n" +
                "</p>\n" +
                "<p>\n" +
                "    <br/>\n" +
                "</p>\n" +
                "<p>\n" +
                "    二、 合同订立\n" +
                "</p>\n" +
                "<p>\n" +
                "    1、本站展示的商品信息（如商品名称、价格、商品描述等）仅构成要约邀请，用户通过本站订购商品，订单即为购买商品的合同，客户在本站的供货商送货完成，并签收后即确认合同，此时合同即告成立。\n" +
                "</p>\n" +
                "<p>\n" +
                "    <br/>\n" +
                "</p>\n" +
                "<p>\n" +
                "    2、 当用户购买的商品发货时，该商品的所有权和灭失风险仍由本站上的供货商承担。\n" +
                "</p>\n" +
                "<p>\n" +
                "    <br/>\n" +
                "</p>\n" +
                "<p>\n" +
                "    3、 在发现本站商品信息错误或缺货的情况下，用户同意本站有撤回或修改该订单信息的权利，并保留对商品订购数量的限制权。用户下订单即表示其对在订单中提供的信息的真实性负责。\n" +
                "</p>\n" +
                "<p>\n" +
                "    <br/>\n" +
                "</p>\n" +
                "<p>\n" +
                "    4、 本站显示的每一商品的价格都包含法律规定的税金，送货费用根据本站的配送政策和用户选择的送货方式另行计收。本站有权更改上述有关价格和送货费用的信息，而不做另行通知。\n" +
                "</p>\n" +
                "<p>\n" +
                "    <br/>\n" +
                "</p>\n" +
                "<p>\n" +
                "    三、 用户的权利和义务\n" +
                "</p>\n" +
                "<p>\n" +
                "    1、 用户有权根据本协议的约定，以及本站上发布的相关规则在本站上查询商品信息、订购具体商品、发表使用体验、参与商品讨论、邀请关注好友、上传商品图片、参加本站的有关活动（如适用），以及享受本站提供的其它服务。\n" +
                "</p>\n" +
                "<p>\n" +
                "    <br/>\n" +
                "</p>\n" +
                "<p>\n" +
                "    2、 用户应当保证在本站购买商品过程中遵守诚实信用原则，不扰乱网上交易的正常秩序。\n" +
                "</p>\n" +
                "<p>\n" +
                "    <br/>\n" +
                "</p>\n" +
                "<p>\n" +
                "    3、 用户享有言论自由权利，并拥有适度修改、删除自己发表的文章的权利。\n" +
                "</p>\n" +
                "<p>\n" +
                "    <br/>\n" +
                "</p>\n" +
                "<p>\n" +
                "    4、 用户不得在本站发表包含以下内容的言论：\n" +
                "</p>\n" +
                "<p>\n" +
                "    ①反对宪法所确定的基本原则，煽动、抗拒、破坏宪法和法律、行政法规实施的；\n" +
                "</p>\n" +
                "<p>\n" +
                "    ② 煽动颠覆国家政权，推翻社会主义制度，煽动、分裂国家，破坏国家统一的；\n" +
                "</p>\n" +
                "<p>\n" +
                "    ③ 损害国家荣誉和利益的；\n" +
                "</p>\n" +
                "<p>\n" +
                "    ④ 煽动民族仇恨、民族歧视，破坏民族团结的；\n" +
                "</p>\n" +
                "<p>\n" +
                "    ⑤ 任何包含对种族、性别、宗教、地域内容等歧视的；\n" +
                "</p>\n" +
                "<p>\n" +
                "    ⑥ 捏造或者歪曲事实，散布谣言，扰乱社会秩序的；\n" +
                "</p>\n" +
                "<p>\n" +
                "    ⑦ 宣扬封建迷信、邪教、淫秽、色情、赌博、暴力、凶杀、恐怖、教唆犯罪的；\n" +
                "</p>\n" +
                "<p>\n" +
                "    ⑧ 公然侮辱他人或者捏造事实诽谤他人的，或者进行其他恶意攻击的；\n" +
                "</p>\n" +
                "<p>\n" +
                "    ⑨ 损害国家机关信誉的；\n" +
                "</p>\n" +
                "<p>\n" +
                "    ⑩ 其他违反宪法和法律行政法规的。\n" +
                "</p>\n" +
                "<p>\n" +
                "    <br/>\n" +
                "</p>\n" +
                "<p>\n" +
                "    四、 本站的权利和义务\n" +
                "</p>\n" +
                "<p>\n" +
                "    1、 本站有义务在现有技术上维护整个网站的正常运行，并努力提升和改进技术，使用户网上交易活动得以顺利进行；由于网络安全问题导致的用户的利益受损，由本站承担相应责任。\n" +
                "</p>\n" +
                "<p>\n" +
                "    <br/>\n" +
                "</p>\n" +
                "<p>\n" +
                "    2、 对于用户在本站上作出下列行为的，本站有权作出删除相关信息、终止提供服务等处理，而无须征得用户的同意：\n" +
                "</p>\n" +
                "<p>\n" +
                "    ① 本站有权对用户的注册信息及购买行为进行查阅，发现注册信息或购买行为中存在任何问题的，有权向用户发出询问及要求改正的通知或者作出删除等处理；\n" +
                "</p>\n" +
                "<p>\n" +
                "    ② 用户违反本协议规定或有违反法律法规和地方规章的行为的，本站有权停止传输并删除其信息，禁止用户发言，限制用户订购，注销用户账户并按照相关法律规定向相关主管部门进行披露；\n" +
                "</p>\n" +
                "<p>\n" +
                "    ③ 对于用户在本站进行的下列行为，本站有权对用户采取删除其信息、禁止用户发言、限制用户订购、注销用户账户等限制性措施：包括(i) 发布或以电子邮件或以其他方式传送存在恶意、虚假和侵犯他人人身财产权利内容的信息；(ii) 进行与网上购物无关或不是以购物为目的的活动，试图扰乱正常购物秩序；(iii) 将有关干扰、破坏或限制任何计算机软件、硬件或通讯设备功能的软件病毒或其他计算机代码、档案和程序之资料，加以上载、发布、发送电子邮件或以其他方式传送；(iv)干扰或破坏本站服务或与本站相连的服务器和网络；或(v) 发布其他违反公共利益或可能严重损害本站和其它用户合法利益的信息。&nbsp;\n" +
                "</p>\n" +
                "<p>\n" +
                "    <br/>\n" +
                "</p>\n" +
                "<p>\n" +
                "    3、 用户在此免费授予本站永久性的独家使用权，并有权对该权利进行再授权，使本站有权在全球范围内全部或部分地使用、复制、修订、改写、发布、翻译和展示用户公示于本站的各类信息，或制作其派生作品，和/或以现在已知或日后开发的任何形式、媒体或技术，将上述信息纳入其它作品内。\n" +
                "</p>\n" +
                "<p>\n" +
                "    <br/>\n" +
                "</p>\n" +
                "<p>\n" +
                "    五、 网站规则\n" +
                "</p>\n" +
                "<p>\n" +
                "    1、 价格变动规则 ：本站将尽最大努力保证用户所购商品或服务在同等质量下价格最低，网站公布价格是客户上次商品采购价格，具体成交价随行就市，由供货商与客户直接洽谈。\n" +
                "</p>\n" +
                "<p>\n" +
                "    <br/>\n" +
                "</p>\n" +
                "<p>\n" +
                "    2、 商品缺货规则：由于市场变化及各种以合理商业努力难以控制的因素的影响，本站无法承诺用户通过提交订单所希望购买的商品都会有货；用户订购的商品或服务如果发生缺货，供货商将会第一时间与客户进行沟通，进行换货、退货或取消订单。\n" +
                "</p>\n" +
                "<p>\n" +
                "    <br/>\n" +
                "</p>\n" +
                "<p>\n" +
                "    3、 邮件/短信服务规则：当用户访问本站或给本站发送电子邮件时，用户同意用电子方式与本站进行联系，并同意以电子方式接受本站的信息。本站有通过邮件和短信的形式，对本站注册、购物用户发送订单信息、促销活动等的权利。如果用户访问本站，或在本站注册、购物，表明其已同意接受此项服务。&nbsp;\n" +
                "</p>\n" +
                "<p>\n" +
                "    <br/>\n" +
                "</p>\n" +
                "<p>\n" +
                "    4、 退换货、补货规则：本站享有对商品退换货、补货的限制权，以及对其具体规则的解释权。本站用户下订单即表明其接受本站的退换货、补货规则。\n" +
                "</p>\n" +
                "<p>\n" +
                "    <br/>\n" +
                "</p>\n" +
                "<p>\n" +
                "    5、 订单取消规则：\n" +
                "</p>\n" +
                "<p>\n" +
                "    本站在下列情况下，可以取消用户订单：\n" +
                "</p>\n" +
                "<p>\n" +
                "    a) 经本站和用户协商达成一致的；\n" +
                "</p>\n" +
                "<p>\n" +
                "    b) 本站上显示的商品信息明显错误或缺货的；\n" +
                "</p>\n" +
                "<p>\n" +
                "    c) 用户订单信息明显错误或用户订购数量超出本站或供货商备货量的；\n" +
                "</p>\n" +
                "<p>\n" +
                "    d) 因不可抗力、本站系统发生故障或遭受第三方攻击，及其它本站无法控制的情形的；\n" +
                "</p>\n" +
                "<p>\n" +
                "    e) 经本站判断不符合公平原则或诚实信用原则的情形（如同一用户多次无理由拒收订购商品）；\n" +
                "</p>\n" +
                "<p>\n" +
                "    f) 按本站已经发布的或将来可能发布或更新的各类规则，可取消用户订单的情形。\n" +
                "</p>\n" +
                "<p>\n" +
                "    <br/>\n" +
                "</p>\n" +
                "<p>\n" +
                "    六、 责任限制与不可抗力\n" +
                "</p>\n" +
                "<p>\n" +
                "    1、 在法律法规所允许的限度内，因使用本站服务而引起的任何损害或经济损失，本站承担的全部责任均不超过用户所购买的与该索赔有关的商品价格。这些责任限制条款将在法律所允许的最大限度内适用，并在用户资格被撤销或终止后仍继续有效。\n" +
                "</p>\n" +
                "<p>\n" +
                "    <br/>\n" +
                "</p>\n" +
                "<p>\n" +
                "    2、 本站仅限在&quot;按现状&quot;和&quot;按现有&quot;的基础上，向用户提供全部的信息、内容、材料、产品(包括软件)和服务。除非另有明确的书面说明,本站不对其包含在本站上的信息、内容、材料、产品(包括软件)或服务作任何形式的、明示或默示的声明或担保，且不会承诺其提供给用户的全部信息或发出的电子邮件没有病毒或其他有害成分。\n" +
                "</p>\n" +
                "<p>\n" +
                "    <br/>\n" +
                "</p>\n" +
                "<p>\n" +
                "    3、 不论在任何情况下，本站均不对由于互联网正常的设备维护，互联网络连接故障，电脑、通讯或其他系统的故障，电力故障，罢工，暴乱，骚乱，灾难性天气（如火灾、洪水、风暴等），爆炸，战争，政府行为，司法行政机关的命令或第三方的不作为而造成的不能履行或延迟履行承担责任。\n" +
                "</p>\n" +
                "<p>\n" +
                "    <br/>\n" +
                "</p>\n" +
                "<p>\n" +
                "    七、 服务的中断和终止：\n" +
                "</p>\n" +
                "<p>\n" +
                "    1、 如用户向本站提出注销用户账号时，经本站审核同意，由本站注销该用户账号，用户即解除与本站的服务协议关系。但注销该用户账号后，本站仍有权：\n" +
                "</p>\n" +
                "<p>\n" +
                "    ① 保留该用户的注册信息及过往的全部交易行为记录；\n" +
                "</p>\n" +
                "<p>\n" +
                "    ② 如用户在注销前在本站上存在违法行为或违反本协议的行为，本站仍可行使本协议所规定的权利。\n" +
                "</p>\n" +
                "<p>\n" +
                "    <br/>\n" +
                "</p>\n" +
                "<p>\n" +
                "    2、 在下列情况下，本站可以通过注销用户账户的方式终止服务：\n" +
                "</p>\n" +
                "<p>\n" +
                "    ① 在用户违反本协议相关规定时，本站有权终止向该用户提供服务。如该用户在本站终止提供服务后，再一次直接或间接或以他人名义注册为本站用户的，本站网有权再次单方面终止向该用户提供服务；\n" +
                "</p>\n" +
                "<p>\n" +
                "    ② 如本站通过用户提供的信息与用户联系时，发现用户在注册时填写的电子邮箱已不存在或无法接收电子邮件的，经本站以其它联系方式通知用户更改，而用户在三个工作日内仍未能提供新的有效电子邮箱地址的，本站有权终止向该用户提供服务；\n" +
                "</p>\n" +
                "<p>\n" +
                "    ③ 一经本站发现用户注册信息中的内容是虚假的，本站有权随时终止向该用户提供服务；\n" +
                "</p>\n" +
                "<p>\n" +
                "    ④ 本协议终止或更新时，用户明示不愿接受新的服务协议的；\n" +
                "</p>\n" +
                "<p>\n" +
                "    ⑤ 本站认为需终止服务的其他情况。\n" +
                "</p>\n" +
                "<p>\n" +
                "    <br/>\n" +
                "</p>\n" +
                "<p>\n" +
                "    八、 适用的法律和管辖权：\n" +
                "</p>\n" +
                "<p>\n" +
                "    本协议适用中华人民共和国的法律，所有的争端将诉诸于本站所在地的人民法院。\n" +
                "</p>\n" +
                "<p>\n" +
                "    <br/>\n" +
                "</p>\n" +
                "<p>\n" +
                "    九、 知识产权：\n" +
                "</p>\n" +
                "<p>\n" +
                "    1、 本站上的图表、标识、网页页眉、按钮图标、文字、服务品名等标示在网站上的信息都是本站运营方及其关联方的财产，受中国和国际知识产权法的保护。未经本站许可，任何人不得使用、复制或用作其他用途。在本站上出现的其他商标是其商标权利人各自的财产，未经本站运营方或相关商标所有人的书面许可，任何第三方都不得使用上述标示在本站上的信息。\n" +
                "</p>\n" +
                "<p>\n" +
                "    <br/>\n" +
                "</p>\n" +
                "<p>\n" +
                "    2、 本站用户发表的文章仅代表作者本人观点，与本站立场无关。作者文责自负。\n" +
                "</p>\n" +
                "<p>\n" +
                "    <br/>\n" +
                "</p>\n" +
                "<p>\n" +
                "    3、 本站及其关联方有权将用户在本网站发表的商品使用体验、商品讨论或图片进行使用或者与其他人合作使用，使用范围包括但不限于网站、电子杂志、杂志、刊物等，使用时需为作者署名，以发表文章时注明的署名为准。文章有附带版权声明者除外。\n" +
                "</p>\n" +
                "<p>\n" +
                "    <br/>\n" +
                "</p>\n" +
                "<p>\n" +
                "    4、 用户在本站上发表的文章及图片（包括转贴的文章及图片）版权仅归原作者所有，若作者有版权声明或原作从其它网站转载而附带有原版权声明者，其版权归属以附带声明为准。\n" +
                "</p>\n" +
                "<p>\n" +
                "    <br/>\n" +
                "</p>\n" +
                "<p>\n" +
                "    5、 任何转载、引用发表于本站的版权文章须符合以下规范：\n" +
                "</p>\n" +
                "<p>\n" +
                "    ① 用于非商业、非盈利、非广告性目的时需注明作者及文章及图片的出处为&quot;本站&quot;；\n" +
                "</p>\n" +
                "<p>\n" +
                "    ② 用于商业、盈利、广告性目的时需征得文章或图片原作者的同意，并注明作者姓名、授权范围及原作出处&quot;本站&quot;；\n" +
                "</p>\n" +
                "<p>\n" +
                "    ③ 任何文章或图片的修改或删除均应保持作者原意并征求原作者同意，并注明授权范围。\n" +
                "</p>\n" +
                "<p>\n" +
                "    <br/>\n" +
                "</p>\n" +
                "<p>\n" +
                "    十、 信息保护：\n" +
                "</p>\n" +
                "<p>\n" +
                "    1、 用户隐私：本站有义务保证用户隐私的安全，但同时有义务对国家相关部门提供因合法原因需本站提供相关用户信息。\n" +
                "</p>\n" +
                "<p>\n" +
                "    <br/>\n" +
                "</p>\n" +
                "<p>\n" +
                "    2、 信息安全\n" +
                "</p>\n" +
                "<p>\n" +
                "    ① 本站账户和提现功能有密码保护，请用户妥善保管账户及密码信息；\n" +
                "</p>\n" +
                "<p>\n" +
                "    ② 如果用户发现自己的个人信息泄密，尤其是本站账户及密码或提现密码发生泄露，请用户立即联络本站客服，以便本站采取相应措施。\n" +
                "</p>\n" +
                "<p>\n" +
                "    <br/>\n" +
                "</p>";
        tv_html.setText(Html.fromHtml(str));
        iv_html_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}
