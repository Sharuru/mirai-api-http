/*
 * Copyright 2020 Mamoe Technologies and contributors.
 *
 * 此源代码的使用受 GNU AFFERO GENERAL PUBLIC LICENSE version 3 许可证的约束, 可以在以下链接找到该许可证.
 * Use of this source code is governed by the GNU AGPLv3 license that can be found through the following link.
 *
 * https://github.com/mamoe/mirai/blob/master/LICENSE
 */

package net.mamoe.mirai.api.http.adapter.http.session

import net.mamoe.mirai.api.http.context.session.AuthedSession

/**
 * 代理到 AuthSession
 *
 * 使用增强设计模式添加未读消息队列
 *
 * @author ryoii
 */
internal class HttpAuthedSession(authedSession: AuthedSession) : AuthedSession by authedSession {
    val unreadQueue: UnreadQueue = UnreadQueue()
}
